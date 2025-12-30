package com.desafio.coupon_api.infrastructure.persistence.repository;

import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.exception.CouponException;
import com.desafio.coupon_api.domain.model.Coupon;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import com.desafio.coupon_api.infrastructure.persistence.entity.CouponEntity;
import com.desafio.coupon_api.infrastructure.persistence.jpa.CouponJpaRepository;
import com.desafio.coupon_api.infrastructure.persistence.mapper.CouponMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository jpaRepository;

    private final CouponMapper mapper;

    public CouponRepositoryImpl(
            CouponJpaRepository jpaRepository,
            CouponMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CouponResponse save(Coupon coupon) {
        CouponEntity entity = mapper.toEntity(coupon);
        CouponEntity saved = jpaRepository.save(entity);
        return mapper.toCouponResponse(saved);
    }


    @Override
    public void delete(String id) {
        CouponEntity couponEntity = jpaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CouponException("Cupom não encontrado."));

        Coupon deletedCoupon  = mapper.toDomain(couponEntity).delete();

        couponEntity.setStatus(deletedCoupon.status());

        jpaRepository.save(couponEntity);

    }

    @Override
    public CouponResponse findById(String id) {
        CouponEntity couponEntity = jpaRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CouponException("Cupom não encontrado."));

        return mapper.toCouponResponse(couponEntity);
    }

}
