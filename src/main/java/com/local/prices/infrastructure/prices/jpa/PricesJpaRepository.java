package com.local.prices.infrastructure.prices.jpa;

import com.local.prices.domain.PricesEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PricesJpaRepository extends JpaRepository<PricesEntity, Long> {

    @EntityGraph(attributePaths = "brand")
    Optional<List<PricesEntity>> findByProductIdAndBrand_Id(Long productId, Long brandId);

    @EntityGraph(attributePaths = "brand")
    Optional<PricesEntity> findTopByProductIdAndBrand_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long productId, Long brandId, LocalDateTime applicationDate1, LocalDateTime applicationDate2
    );
}
