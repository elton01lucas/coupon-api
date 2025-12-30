package com.desafio.coupon_api.application.usecase.coupon.response;

import com.desafio.coupon_api.domain.model.CouponStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
        String id,
        String code,
        String description,
        LocalDateTime expirationDate,
        BigDecimal discountValue,
        CouponStatus status,
        boolean published,
        boolean redeemed
){
}
