package com.local.prices.utils;

import com.local.prices.application.model.PricesModel;
import com.local.prices.domain.BrandEntity;
import com.local.prices.domain.PricesEntity;
import com.local.prices.router.dto.PricesDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestDataFactory {

    public static PricesModel buildPricesModelCase1() {
        PricesModel pricesModel = new PricesModel();
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(new BrandEntity(1L, "ZARA"));
        pricesModel.setStartDate("2020-06-14T10:00:00");
        pricesModel.setEndDate("2020-06-14T18:00:00");
        pricesModel.setPrice(BigDecimal.valueOf(35.50));
        pricesModel.setCurr("EUR");
        return pricesModel;
    }

    public static PricesDTO buildPricesDtoCase1() {
        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(new BrandEntity(1L, "ZARA"));
        pricesDto.setStartDate("2020-06-14T10:00:00");
        pricesDto.setEndDate("2020-06-14T18:00:00");
        pricesDto.setPrice(BigDecimal.valueOf(35.50));
        pricesDto.setCurr("EUR");
        return pricesDto;
    }

    public static PricesModel buildPricesModelCase2() {

        PricesModel pricesModel = new PricesModel();
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(new BrandEntity(1L, "ZARA"));
        pricesModel.setStartDate("2020-06-14T16:00:00");
        pricesModel.setEndDate("2020-06-14T18:00:00");
        pricesModel.setPrice(new BigDecimal("35.50"));
        pricesModel.setCurr("EUR");
        return pricesModel;
    }

    public static PricesDTO buildPricesDtoCase2() {

        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(new BrandEntity(1L, "ZARA"));
        pricesDto.setStartDate("2020-06-14T16:00:00");
        pricesDto.setEndDate("2020-06-14T18:00:00");
        pricesDto.setPrice(new BigDecimal("35.50"));
        pricesDto.setCurr("EUR");
        return pricesDto;
    }

    public static PricesModel buildPricesModelCase3() {

        PricesModel pricesModel = new PricesModel();
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(new BrandEntity(1L, "ZARA"));
        pricesModel.setStartDate("2020-06-14T21:00:00");
        pricesModel.setEndDate("2020-06-14T23:59:59");
        pricesModel.setPrice(new BigDecimal("35.50"));
        pricesModel.setCurr("EUR");
        return pricesModel;
    }

    public static PricesDTO buildPricesDtoCase3() {

        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(new BrandEntity(1L, "ZARA"));
        pricesDto.setStartDate("2020-06-14T21:00:00");
        pricesDto.setEndDate("2020-06-14T23:59:59");
        pricesDto.setPrice(new BigDecimal("35.50"));
        pricesDto.setCurr("EUR");
        return pricesDto;
    }

    public static PricesModel buildPricesModelCase4() {

        PricesModel pricesModel = new PricesModel();
        pricesModel.setId(4L);
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(new BrandEntity(1L, "ZARA"));
        pricesModel.setStartDate("2020-06-15T00:00:00");
        pricesModel.setEndDate("2020-06-15T11:00:00");
        pricesModel.setPriceList(4);
        pricesModel.setPriority(1);
        pricesModel.setPrice(new BigDecimal("30.50"));
        pricesModel.setCurr("EUR");

        return pricesModel;
    }

    public static PricesDTO buildPricesDtoCase4() {

        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setId(4L);
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(new BrandEntity(1L, "ZARA"));
        pricesDto.setStartDate("2020-06-15T00:00:00");
        pricesDto.setEndDate("2020-06-15T11:00:00");
        pricesDto.setPriceList(4);
        pricesDto.setPriority(1);
        pricesDto.setPrice(new BigDecimal("30.50"));
        pricesDto.setCurr("EUR");
        return pricesDto;
    }

    public static PricesModel buildPricesModelCase5() {


        PricesModel pricesModel = new PricesModel();
        pricesModel.setId(5L);
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(new BrandEntity(1L, "ZARA"));
        pricesModel.setStartDate("2020-06-15T16:00:00");
        pricesModel.setEndDate("2020-12-31T23:59:59");
        pricesModel.setPriceList(5);
        pricesModel.setPriority(1);
        pricesModel.setPrice(new BigDecimal("38.95"));
        pricesModel.setCurr("EUR");
        return pricesModel;
    }

    public static PricesDTO buildPricesDtoCase5() {

        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setId(5L);
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(new BrandEntity(1L, "ZARA"));
        pricesDto.setStartDate("2020-06-15T16:00:00");
        pricesDto.setEndDate("2020-12-31T23:59:59");
        pricesDto.setPriceList(5);
        pricesDto.setPriority(1);
        pricesDto.setPrice(new BigDecimal("38.95"));
        pricesDto.setCurr("EUR");
        return pricesDto;
    }

    public static PricesEntity buildPricesDtoOutOfDate() {

        PricesEntity pricesEntityOutRange = new PricesEntity();
        pricesEntityOutRange.setId(2L);
        pricesEntityOutRange.setProductId(35455L);
        pricesEntityOutRange.setStartDate(LocalDateTime.parse("2020-06-14T10:00:00"));
        pricesEntityOutRange.setEndDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        pricesEntityOutRange.setPrice(BigDecimal.valueOf(25.50));
        pricesEntityOutRange.setPriority(1);
        pricesEntityOutRange.setPriceList(2);
        return pricesEntityOutRange;
    }

    public static PricesModel buildPricesModelOutOfDate() {

        PricesModel pricesModelOutRange = new PricesModel();
        pricesModelOutRange.setId(2L);
        pricesModelOutRange.setProductId(35455L);
        pricesModelOutRange.setStartDate("2020-06-14T10:00:00");
        pricesModelOutRange.setEndDate("2020-06-14T15:00:00");
        pricesModelOutRange.setPrice(BigDecimal.valueOf(25.50));
        pricesModelOutRange.setPriority(1);
        pricesModelOutRange.setPriceList(2);
        pricesModelOutRange.setCurr("EUR");
        return pricesModelOutRange;
    }

    public static BrandEntity buildBrandEntity() {
        BrandEntity brand = new BrandEntity();
        brand.setId(1L);
        brand.setName("ZARA");
        return brand;
    }
}
