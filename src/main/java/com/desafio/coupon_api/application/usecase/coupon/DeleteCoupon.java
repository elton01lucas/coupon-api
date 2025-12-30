package com.desafio.coupon_api.application.usecase.coupon;

import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteCoupon {
    private final CouponRepository couponRepository;

    public DeleteCoupon(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void execute(String id) {
        couponRepository.delete(id);
    }

}
