package com.desafio.coupon_api.domain.model;

import com.desafio.coupon_api.domain.exception.CouponException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    private static final String CODE = "ABC123";
    private static final String CODE_ESPECIAL_CHAR = "A!B@C#1$2%$3";

    @Test
    void shouldSoftDeleteCouponUsingStatus() {
        Coupon coupon = couponStub(CODE);

        Coupon deleted = coupon.delete();

        assertEquals(CouponStatus.DELETED, deleted.status());
    }

    @Test
    void shouldNotDeleteCouponTwice() {
        Coupon coupon = couponStub(CODE);

        Coupon deleted = coupon.delete();

        assertThrows(CouponException.class, deleted::delete);
    }

    @Test
    void shouldRemoveSpecialCharFromCode() {
        Coupon coupon = couponStub(CODE_ESPECIAL_CHAR);

        assertEquals(CODE, coupon.code());
        assertEquals(6, coupon.code().length());
    }

    @Test
    void shouldValidateDiscount() {
        Coupon coupon = couponStub(CODE);

        assertEquals(BigDecimal.valueOf(10), coupon.discountValue());
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsLessThanMinimum() {
        assertThrows(CouponException.class, () ->
                Coupon.create(
                        CODE,
                        "Invalid discount",
                        BigDecimal.valueOf(0.4), // menor que 0.5
                        LocalDateTime.now().plusDays(10),
                        true
                )
        );
    }

    @Test
    void shouldValidateExpirationDate() {
        Coupon coupon = couponStub(CODE);

        assertEquals(LocalDateTime.now().plusDays(10), coupon.expirationDate());
    }

    @Test
    void shouldThrowExceptionWhenExpirationDateIsInPast() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        assertThrows(CouponException.class, () ->
                Coupon.create(
                        CODE,
                        "Invalid expiration time",
                        BigDecimal.valueOf(10),
                        pastDate,
                        true
                )
        );
    }


    private Coupon couponStub(String code) {
        return Coupon.create(
                code,
                "10% off",
                BigDecimal.valueOf(10),
                LocalDateTime.now().plusDays(10),
                true
        );
    }
}
