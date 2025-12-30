package com.desafio.coupon_api.infrastructure.persistence.jpa;

import com.desafio.coupon_api.infrastructure.persistence.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, UUID> {
}
