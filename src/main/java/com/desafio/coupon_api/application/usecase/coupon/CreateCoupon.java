package com.desafio.coupon_api.application.usecase.coupon;

import com.desafio.coupon_api.application.usecase.coupon.request.CreateCouponRequest;
import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.model.Coupon;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCoupon {

    private final CouponRepository couponRepository;

    public CreateCoupon(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public CouponResponse execute(CreateCouponRequest request) {
        return couponRepository.save(Coupon.create(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        ));
    }
}
