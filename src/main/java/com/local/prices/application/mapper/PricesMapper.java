package com.local.prices.application.mapper;

import com.local.prices.application.model.PricesModel;
import com.local.prices.domain.PricesEntity;
import com.local.prices.router.dto.PricesDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PricesMapper {

    PricesDTO toPricesDTO(PricesModel pricesModel);

    PricesModel toPricesModel(PricesDTO pricesDTO);

    PricesEntity toPrices(PricesModel pricesModel);

    PricesModel toPricesModel(PricesEntity pricesEntity);

    List<PricesModel> toPricesModelList(List<PricesEntity> pricesEntities);
}
