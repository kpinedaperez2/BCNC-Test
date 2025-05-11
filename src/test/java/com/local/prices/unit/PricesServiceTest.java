package com.local.prices.unit;

import com.local.prices.application.exception.PricesCollectionNotFoundException;
import com.local.prices.application.exception.PricesServiceException;
import com.local.prices.application.mapper.PricesMapper;
import com.local.prices.application.model.PricesModel;
import com.local.prices.application.usecase.impl.PricesServiceImpl;
import com.local.prices.domain.BrandEntity;
import com.local.prices.domain.PricesEntity;
import com.local.prices.infrastructure.db.jpa.PricesJpaRepository;
import com.local.prices.router.dto.RateRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.local.prices.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PricesServiceTest {

    @InjectMocks
    private PricesServiceImpl pricesService;

    @Mock
    private PricesJpaRepository pricesJpaRepository;

    @Mock
    private PricesMapper pricesMapper;

    private RateRequest rateRequest;

    private PricesEntity pricesEntity;
    private PricesModel pricesModel;

    private PricesEntity pricesEntityOutRange;
    private PricesModel pricesModelOutRange;

    BrandEntity brandEntity = new BrandEntity();

    @BeforeEach
    void setUp() {
        rateRequest = new RateRequest();
        rateRequest.setProductId(35455L);
        rateRequest.setBrandId(1L);
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T10:00:00"));

        pricesEntity = new PricesEntity();
        pricesEntity.setId(1L);
        pricesEntity.setProductId(35455L);

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        pricesEntity.setPrice(BigDecimal.valueOf(35.50));
        pricesEntity.setPriority(1);
        pricesEntity.setPriceList(1);

        pricesModel = new PricesModel();
        pricesModel.setId(1L);
        pricesModel.setProductId(35455L);
        pricesModel.setStartDate("2020-06-14T10:00:00");
        pricesModel.setEndDate("2020-06-14T18:30:00");
        pricesModel.setPrice(BigDecimal.valueOf(35.50));
        pricesModel.setPriority(1);
        pricesModel.setPriceList(1);
        pricesModel.setCurr("EUR");

        pricesEntityOutRange = buildPricesDtoOutOfDate();
        pricesModelOutRange = buildPricesModelOutOfDate();

        brandEntity = buildBrandEntity();
    }

    @Test
    void shouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));
        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        anyLong(), anyLong()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);
        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase1shouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));

        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T10:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        pricesEntity.setPriority(0);

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        35455L, brandEntity.getId()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase2ShouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));

        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T16:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        35455L, brandEntity.getId()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase3ShouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));

        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T21:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T20:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-14T23:59:59"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        35455L, brandEntity.getId()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase4ShouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));

        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-15T10:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        35455L, brandEntity.getId()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase5ShouldReturnPricesModelWhenFound() {
        Optional<List<PricesEntity>> optionalPricesEntityList = Optional.of(List.of(pricesEntity));

        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-16T21:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-15T16:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        35455L, brandEntity.getId()))
                .thenReturn(optionalPricesEntityList);
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.apply(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void shouldNotReturnPriceWhenDateIsAfterEndDate() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T16:00:00"));

        when(pricesJpaRepository.findByProductIdAndBrand_Id(35455L, brandEntity.getId()))
                .thenReturn(Optional.of(List.of(pricesEntityOutRange)));

        assertThrows(EntityNotFoundException.class, () -> pricesService.apply(rateRequest));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenNoPriceFound() {
        Optional<List<PricesEntity>> emptyOptional = Optional.empty();
        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        anyLong(), anyLong()))
                .thenReturn(emptyOptional);
        assertThrows(EntityNotFoundException.class, () -> pricesService.apply(rateRequest));
    }

    @Test
    void shouldThrowPricesCollectionNotFoundException() {
        PricesCollectionNotFoundException exception = new PricesCollectionNotFoundException("not found");

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        anyLong(), anyLong()))
                .thenThrow(exception);

        PricesCollectionNotFoundException thrown = assertThrows(
                PricesCollectionNotFoundException.class,
                () -> pricesService.apply(rateRequest)
        );

        assertEquals("not found", thrown.getMessage());
    }

    @Test
    void shouldThrowPricesServiceExceptionWhenDataAccessFails() {
        DataAccessException dataAccessException = new DataAccessResourceFailureException("DB error");

        when(pricesJpaRepository
                .findByProductIdAndBrand_Id(
                        anyLong(), anyLong()))
                .thenThrow(dataAccessException);

        PricesServiceException thrown = assertThrows(
                PricesServiceException.class,
                () -> pricesService.apply(rateRequest)
        );

        assertTrue(thrown.getMessage().contains("Cannot get product info for database access error"));
    }

    @Test
    void testCase1shouldServiceV2ReturnPricesModelWhenFound() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T10:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        pricesEntity.setPriority(0);

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        35455L, brandEntity.getId(), rateRequest.getApplicationDate(), rateRequest.getApplicationDate()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase2ShouldServiceV2ReturnPricesModelWhenFound() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T16:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        35455L, brandEntity.getId(), rateRequest.getApplicationDate(), rateRequest.getApplicationDate()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase3ShouldServiceV2ReturnPricesModelWhenFound() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-14T21:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-14T20:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-14T23:59:59"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        35455L, brandEntity.getId(), rateRequest.getApplicationDate(), rateRequest.getApplicationDate()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase4ShouldServiceV2ReturnPricesModelWhenFound() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-15T10:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        35455L, brandEntity.getId(), rateRequest.getApplicationDate(), rateRequest.getApplicationDate()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void testCase5ShouldServiceV2ReturnPricesModelWhenFound() {
        rateRequest.setApplicationDate(LocalDateTime.parse("2020-06-16T21:00:00"));

        pricesEntity.setStartDate(LocalDateTime.parse("2020-06-15T16:00:00"));
        pricesEntity.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        pricesEntity.setPriority(1);

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        35455L, brandEntity.getId(), rateRequest.getApplicationDate(), rateRequest.getApplicationDate()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void shouldServiceV2ReturnPricesModelWhenFound() {
        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        anyLong(), anyLong(), any(), any()))
                .thenReturn(Optional.of(pricesEntity));
        when(pricesMapper.toPricesModel(pricesEntity)).thenReturn(pricesModel);

        PricesModel result = pricesService.applyV2(rateRequest);

        assertEquals(pricesModel, result);
    }

    @Test
    void shouldServiceV2ThrowEntityNotFoundExceptionWhenNoPriceFound() {
        Optional<PricesEntity> emptyOptional = Optional.empty();
        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        anyLong(), anyLong(), any(), any()))
                .thenReturn(emptyOptional);
        assertThrows(EntityNotFoundException.class, () -> pricesService.applyV2(rateRequest));
    }

    @Test
    void shouldServiceV2ThrowPricesCollectionNotFoundException() {
        PricesCollectionNotFoundException exception = new PricesCollectionNotFoundException("not found");

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        anyLong(), anyLong(), any(), any()))
                .thenThrow(exception);

        PricesCollectionNotFoundException thrown = assertThrows(
                PricesCollectionNotFoundException.class,
                () -> pricesService.applyV2(rateRequest)
        );

        assertEquals("not found", thrown.getMessage());
    }

    @Test
    void shouldServiceV2ThrowPricesServiceExceptionWhenDataAccessFails() {
        DataAccessException dataAccessException = new DataAccessResourceFailureException("DB error");

        when(pricesJpaRepository
                .findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        anyLong(), anyLong(), any(), any()))
                .thenThrow(dataAccessException);

        PricesServiceException thrown = assertThrows(
                PricesServiceException.class,
                () -> pricesService.applyV2(rateRequest)
        );

        assertTrue(thrown.getMessage().contains("Cannot get product info for database access error"));
    }
}
