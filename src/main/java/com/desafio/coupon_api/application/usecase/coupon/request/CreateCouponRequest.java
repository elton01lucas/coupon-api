package com.desafio.coupon_api.application.usecase.coupon.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCouponRequest(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        boolean published
) {}