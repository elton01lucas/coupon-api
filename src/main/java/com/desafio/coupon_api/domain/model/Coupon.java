package com.desafio.coupon_api.domain.model;

import com.desafio.coupon_api.domain.exception.CouponException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Coupon(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDateTime expirationDate,
        Boolean published,
        CouponStatus status,
        Boolean redeemed
) {

    public static Coupon create(
            String code,
            String description,
            BigDecimal discountValue,
            LocalDateTime expirationDate,
            Boolean published
    ) {
        String normalizedCode = normalizeCode(code);

        validateCode(normalizedCode);
        validateDiscount(discountValue);
        validateExpirationDate(expirationDate);

        return new Coupon(
                normalizedCode,
                description,
                discountValue,
                expirationDate,
                published,
                getStatusByPublished(published),
                false
        );
    }

    public Coupon delete() {
        if (this.status == CouponStatus.DELETED) {
            throw new CouponException("Coupon already deleted");
        }

        return new Coupon(
                this.code,
                this.description,
                this.discountValue,
                this.expirationDate,
                this.published,
                CouponStatus.DELETED,
                this.redeemed
        );
    }

    private static void validateCode(String code) {
        if (code == null || code.length() != 6) {
            throw new CouponException("Coupon code must have exactly 6 characters");
        }
    }

    private static void validateDiscount(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new CouponException("Discount value must be at least 0.5");
        }
    }

    private static void validateExpirationDate(LocalDateTime dateTime) {
        LocalDate today = LocalDate.now();
        LocalDate expirationDate = dateTime.toLocalDate();

        if (expirationDate.isBefore(today)) {
            throw new CouponException("Expiration date cannot be in the past");
        }
    }

    private static String normalizeCode(String code) {
        return code.replaceAll("[^a-zA-Z0-9]", "");
    }

    private static CouponStatus getStatusByPublished(Boolean published) {
        return published != null && published ? CouponStatus.ACTIVE : CouponStatus.INACTIVE;
    }

}
