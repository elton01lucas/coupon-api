package com.desafio.coupon_api.application.usecase.coupon;

import com.desafio.coupon_api.domain.exception.CouponException;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class DeleteCouponTest {
    private CouponRepository couponRepository;
    private DeleteCoupon deleteCoupon;

    @BeforeEach
    void setUp() {
        couponRepository = mock(CouponRepository.class);
        deleteCoupon = new DeleteCoupon(couponRepository);
    }

    @Test
    void shouldDeleteCouponSuccessfully() {
        String couponId = UUID.randomUUID().toString();

        deleteCoupon.execute(couponId);
        verify(couponRepository, times(1)).delete(couponId);
    }

    @Test
    void shouldThrowExceptionWhenCouponNotFound() {

        String couponId = UUID.randomUUID().toString();
        doThrow(new CouponException("Cupom não encontrado.")).when(couponRepository).delete(couponId);

        Assertions.assertThatThrownBy(() -> deleteCoupon.execute(couponId))
                .isInstanceOf(CouponException.class)
                .hasMessage("Cupom não encontrado.");

        verify(couponRepository, times(1)).delete(couponId);
    }
}
