package com.local.prices.application.usecase.impl;

import com.local.prices.application.exception.PricesCollectionNotFoundException;
import com.local.prices.application.exception.PricesServiceException;
import com.local.prices.application.mapper.PricesMapper;
import com.local.prices.application.model.PricesModel;
import com.local.prices.application.usecase.PricesService;
import com.local.prices.domain.PricesEntity;
import com.local.prices.infrastructure.db.jpa.PricesJpaRepository;
import com.local.prices.router.dto.RateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PricesServiceImpl implements PricesService {

    private final PricesJpaRepository pricesJpaRepository;
    private final PricesMapper pricesMapper;

    public PricesServiceImpl(PricesJpaRepository pricesJpaRepository, PricesMapper pricesMapper) {
        this.pricesJpaRepository = pricesJpaRepository;
        this.pricesMapper = pricesMapper;
    }

    /**
     * Applies the pricing logic for a product and brand based on the given rate request.
     *
     * This method finds the applicable price for a given product and brand, considering the application date
     * and the highest priority price.
     *
     * @param rateRequest the {@link RateRequest} containing the product ID, brand ID, and application date.
     * @return the {@link PricesModel} with the applicable price.
     * @throws EntityNotFoundException if no price data or applicable price is found.
     * @throws PricesServiceException if there is a database access error.
     */
    @Transactional(readOnly = true)
    @Override
    public PricesModel apply(RateRequest rateRequest) {
        try {
            List<PricesEntity> prices = pricesJpaRepository
                    .findByProductIdAndBrand_Id(
                            rateRequest.getProductId(),
                            rateRequest.getBrandId()
                    ).orElseThrow(() -> new EntityNotFoundException(
                            "No price table data found for product ID " + rateRequest.getProductId() +
                                    " and brand ID " + rateRequest.getBrandId()
                    ));
            log.info("Product info has been found for ID {} and brand ID {}: {}", rateRequest.getProductId(), rateRequest.getBrandId(), prices);
            return getAndFilterPriceModel(rateRequest, prices)
                    .orElseThrow(() -> new EntityNotFoundException("No applicable price found for date " + rateRequest.getApplicationDate()));

        } catch (PricesCollectionNotFoundException e) {
            log.error("Product not found with id: {}", rateRequest.getProductId(), e);
            throw e;
        } catch (DataAccessException e) {
            log.error("Database error with id: {}", rateRequest.getProductId(), e);
            throw new PricesServiceException("Cannot get product info for database access error", e);
        }
    }

    /**
     * Applies the pricing logic for a product and brand based on the given rate request.
     *
     * This method finds the applicable price for a given product and brand, considering the application date
     * and the highest priority price.
     *
     * @param pricesRequest the {@link RateRequest} containing the product ID, brand ID, and application date.
     * @return the {@link PricesModel} with the applicable price.
     * @throws EntityNotFoundException if no price data or applicable price is found.
     * @throws PricesServiceException if there is a database access error.
     */
    @Override
    public PricesModel applyV2(RateRequest pricesRequest) {
        try {
            return pricesJpaRepository.findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                    pricesRequest.getProductId(),
                    pricesRequest.getBrandId(),
                    pricesRequest.getApplicationDate(),
                    pricesRequest.getApplicationDate()
            ).map(pricesMapper::toPricesModel).orElseThrow(() -> new EntityNotFoundException("No price table data found for product ID "
                    + pricesRequest.getProductId() + " and brand ID " + pricesRequest.getBrandId()));

        } catch (PricesCollectionNotFoundException e) {
            log.error("Product not found with id: {}", pricesRequest.getProductId(), e);
            throw e;
        } catch (DataAccessException e) {
            log.error("Database error with id: {}", pricesRequest.getProductId(), e);
            throw new PricesServiceException("Cannot get product info for database access error", e);
        }
    }

    private Optional<PricesModel> getAndFilterPriceModel(RateRequest rateRequest, List<PricesEntity> prices) {
        return prices.stream()
                .filter(price -> !rateRequest.getApplicationDate().isBefore(price.getStartDate())
                        && !rateRequest.getApplicationDate().isAfter(price.getEndDate()))
                .max(Comparator.comparingInt(PricesEntity::getPriority))
                .map(pricesMapper::toPricesModel);
    }
}
