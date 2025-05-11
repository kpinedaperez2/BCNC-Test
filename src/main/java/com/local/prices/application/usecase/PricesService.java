package com.local.prices.application.usecase;

import com.local.prices.application.model.PricesModel;
import com.local.prices.router.dto.RateRequest;

import java.util.function.Function;

public interface PricesService extends Function<RateRequest, PricesModel> {

    PricesModel applyV2(RateRequest pricesRequest);
}
