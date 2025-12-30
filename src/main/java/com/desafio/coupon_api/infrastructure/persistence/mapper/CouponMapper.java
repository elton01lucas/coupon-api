package com.desafio.coupon_api.infrastructure.persistence.mapper;

import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.model.Coupon;
import com.desafio.coupon_api.infrastructure.persistence.entity.CouponEntity;
import org.springframework.stereotype.Component;


@Component
public class CouponMapper {

    public CouponResponse toCouponResponse(CouponEntity entity) {
        return new CouponResponse(
                entity.getId().toString(),
                entity.getCode(),
                entity.getDescription(),
                entity.getExpirationDate(),
                entity.getDiscountValue(),
                entity.getStatus(),
                entity.getPublished(),
                entity.getRedeemed()
        );
    }

    public CouponEntity toEntity(Coupon coupon) {
        CouponEntity entity = new CouponEntity();
        entity.setCode(coupon.code());
        entity.setDiscountValue(coupon.discountValue());
        entity.setDescription(coupon.description());
        entity.setStatus(coupon.status());
        entity.setExpirationDate(coupon.expirationDate());
        entity.setPublished(coupon.published());
        entity.setRedeemed(coupon.redeemed());
        return entity;
    }

    public Coupon toDomain(CouponEntity entity) {
        return new Coupon(
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getPublished(),
                entity.getStatus(),
                entity.getRedeemed());
    }
}
