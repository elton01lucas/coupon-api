package com.desafio.coupon_api.application.usecase.coupon;

import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCoupon {

    private final CouponRepository couponRepository;

    public GetCoupon(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public CouponResponse execute(String id) {
        return couponRepository.findById(id);
    }
}
