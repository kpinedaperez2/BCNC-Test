package com.local.prices.router;

import com.local.prices.application.mapper.PricesMapper;
import com.local.prices.application.usecase.PricesService;
import com.local.prices.router.dto.PricesDTO;
import com.local.prices.router.dto.RateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bcnc")
public class PricesController {

    private final PricesMapper pricesMapper;
    private final PricesService pricesService;

    public PricesController(PricesMapper pricesMapper, PricesService pricesService) {
        this.pricesMapper = pricesMapper;
        this.pricesService = pricesService;
    }

    /**
     * Applies a rate based on the productId, brandId and applicationDate.
     *
     * @param pricesRequest the {@link RateRequest} object containing the necessary information
     *                      to apply a rate (must be valid).
     * @return a {@link ResponseEntity} containing a {@link PricesDTO} with the applied rate data.
     */
    @PostMapping("/applyRate")
    public ResponseEntity<PricesDTO> applyRate(@Valid @RequestBody RateRequest pricesRequest) {
        PricesDTO response = pricesMapper.toPricesDTO(pricesService.apply(pricesRequest));
        return ResponseEntity.ok(response);
    }

    /**
     * Applies a rate using version 2 of the rate application logic, based on the productId, brandId and applicationDate.
     *
     * @param pricesRequest the {@link RateRequest} object containing the necessary information
     *                      to apply a rate (must be valid).
     * @return a {@link ResponseEntity} containing a {@link PricesDTO} with the applied rate data.
     */
    @PostMapping("/applyRate/v2")
    public ResponseEntity<PricesDTO> applyRateV2(@Valid @RequestBody RateRequest pricesRequest) {
        PricesDTO response = pricesMapper.toPricesDTO(pricesService.applyV2(pricesRequest));
        return ResponseEntity.ok(response);
    }
}