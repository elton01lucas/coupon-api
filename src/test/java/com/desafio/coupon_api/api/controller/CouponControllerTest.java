package com.desafio.coupon_api.api.controller;
import com.desafio.coupon_api.application.usecase.coupon.CreateCoupon;
import com.desafio.coupon_api.application.usecase.coupon.DeleteCoupon;
import com.desafio.coupon_api.application.usecase.coupon.GetCoupon;
import com.desafio.coupon_api.application.usecase.coupon.request.CreateCouponRequest;
import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.model.CouponStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class CouponControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CreateCoupon createCoupon;

    @Mock
    private GetCoupon getCoupon;

    @Mock
    private DeleteCoupon deleteCoupon;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // <-- importante
        mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
    }

    @Test
    void shouldCreateCoupon() throws Exception {
        CreateCouponRequest request = createCouponRequestStub();

        CouponResponse response = couponResponseStub();

        when(createCoupon.execute(any(CreateCouponRequest.class))).thenReturn(response);

        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.code").value("ABC123"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldGetCoupon() throws Exception {
        CouponResponse response = couponResponseStub();

        when(getCoupon.execute("uuid-123")).thenReturn(response);

        mockMvc.perform(get("/coupons/uuid-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void shouldDeleteCoupon() throws Exception {
        doNothing().when(deleteCoupon).execute("uuid-123");

        mockMvc.perform(delete("/coupons/uuid-123"))
                .andExpect(status().isNoContent());
    }

    private CreateCouponRequest createCouponRequestStub(){
        return new CreateCouponRequest(
                "ABC-123",
                "Cupom teste",
                BigDecimal.valueOf(0.5),
                LocalDateTime.of(2025, 12, 31, 23, 59, 59),
                true
        );
    }

    private CouponResponse couponResponseStub(){
        return new CouponResponse(
                "uuid-123",
                "ABC123",
                "Cupom teste",
                LocalDateTime.of(2025, 12, 31, 23, 59, 59),
                BigDecimal.valueOf(0.5),
                CouponStatus.ACTIVE,
                true,
                false
        );
    }
}