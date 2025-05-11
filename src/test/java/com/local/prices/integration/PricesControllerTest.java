package com.local.prices.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.local.prices.application.mapper.PricesMapper;
import com.local.prices.application.model.PricesModel;
import com.local.prices.application.usecase.PricesService;
import com.local.prices.domain.BrandEntity;
import com.local.prices.router.PricesController;
import com.local.prices.router.dto.PricesDTO;
import com.local.prices.router.dto.RateRequest;
import com.local.prices.utils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.local.prices.utils.TestDataFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PricesController.class)
public class PricesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PricesMapper pricesMapper;

    @MockBean
    private PricesService pricesService;

    @Autowired
    private ObjectMapper objectMapper;

    PricesModel pricesModel = new PricesModel();
    PricesDTO pricesDto = new PricesDTO();

    @Test
    public void testApplyRateEndpoint() throws Exception {

        BrandEntity brand = new BrandEntity();
        brand.setId(1L);
        brand.setName("ZARA");

        PricesModel pricesModel = new PricesModel();
        pricesModel.setProductId(35455L);
        pricesModel.setBrand(brand);

        PricesDTO pricesDto = new PricesDTO();
        pricesDto.setProductId(35455L);
        pricesDto.setBrand(brand);

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(
                        post("/bcnc/applyRate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pricesModel))
                )
                .andExpect(status().isOk());

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRate_ValidDateCase1() throws Exception {

        pricesModel = buildPricesModelCase1();
        pricesDto = buildPricesDtoCase1();

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRate_ValidDateCase2() throws Exception {

        pricesModel = buildPricesModelCase2();
        pricesDto = buildPricesDtoCase2();

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRate_ValidDateCase3() throws Exception {
        pricesModel = buildPricesModelCase3();
        pricesDto = buildPricesDtoCase3();

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRate_Case4() throws Exception {
        pricesModel = buildPricesModelCase4();
        pricesDto = buildPricesDtoCase4();

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RateRequest(35455L, 1L, LocalDateTime.parse("2020-06-15T10:00:00")))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRate_Case5() throws Exception {
        pricesModel =buildPricesModelCase5();
        pricesDto = buildPricesDtoCase5();

        when(pricesService.apply(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RateRequest(35455L, 1L, LocalDateTime.parse("2020-06-16T21:00:00")))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).apply(any());
    }

    @Test
    void testApplyRateV2_ValidDateCase1() throws Exception {

        pricesModel = buildPricesModelCase1();
        pricesDto = buildPricesDtoCase1();

        when(pricesService.applyV2(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).applyV2(any());
    }

    @Test
    void testApplyRateV2_ValidDateCase2() throws Exception {
        pricesModel = buildPricesModelCase2();
        pricesDto = buildPricesDtoCase2();

        when(pricesService.applyV2(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).applyV2(any());
    }

    @Test
    void testApplyRateV2_ValidDateCase3() throws Exception {

        pricesModel = buildPricesModelCase3();
        pricesDto = buildPricesDtoCase3();

        when(pricesService.applyV2(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesModel)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).applyV2(any());
    }

    @Test
    void testApplyRateV2_Case4() throws Exception {
        pricesModel = buildPricesModelCase4();
        pricesDto = buildPricesDtoCase4();

        when(pricesService.applyV2(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RateRequest(35455L, 1L, LocalDateTime.parse("2020-06-15T10:00:00")))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).applyV2(any());
    }

    @Test
    void testApplyRateV2_Case5() throws Exception {
        pricesModel = buildPricesModelCase5();
        pricesDto = buildPricesDtoCase5();

        when(pricesService.applyV2(any())).thenReturn(pricesModel);
        when(pricesMapper.toPricesDTO(pricesModel)).thenReturn(pricesDto);

        mockMvc.perform(post("/bcnc/applyRate/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RateRequest(35455L, 1L, LocalDateTime.parse("2020-06-16T21:00:00")))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(pricesDto)));

        verify(pricesService, times(1)).applyV2(any());
    }

}
