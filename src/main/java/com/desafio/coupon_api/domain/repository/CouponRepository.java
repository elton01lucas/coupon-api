package com.desafio.coupon_api.domain.repository;

import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.model.Coupon;


public interface CouponRepository {
    CouponResponse save(Coupon coupon);
    void delete(String id);
    CouponResponse findById(String id);
}
