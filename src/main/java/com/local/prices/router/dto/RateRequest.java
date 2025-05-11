package com.local.prices.router.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateRequest {

    @NotNull(message = "brandId is required")
    private Long brandId;
    @NotNull(message = "productId is required")
    private Long productId;
    @NotNull(message = "applicationDate is required")
    private LocalDateTime applicationDate;

}
