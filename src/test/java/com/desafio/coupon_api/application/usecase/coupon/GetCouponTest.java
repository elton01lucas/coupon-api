package com.desafio.coupon_api.application.usecase.coupon;
import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.exception.CouponException;
import com.desafio.coupon_api.domain.model.CouponStatus;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class GetCouponTest {

    private CouponRepository couponRepository;
    private GetCoupon getCoupon;

    @BeforeEach
    void setUp() {
        couponRepository = mock(CouponRepository.class);
        getCoupon = new GetCoupon(couponRepository);
    }

    @Test
    void shouldReturnCouponSuccessfully() {
        String couponId = UUID.randomUUID().toString();
        CouponResponse response = couponResponseStub(couponId);

        when(couponRepository.findById(couponId)).thenReturn(response);

        CouponResponse result = getCoupon.execute(couponId);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.id()).isEqualTo(couponId);
        Assertions.assertThat(result.code()).isEqualTo("ABC123");
        Assertions.assertThat(result.status()).isEqualTo(CouponStatus.ACTIVE);

        verify(couponRepository, times(1)).findById(couponId);
    }

    @Test
    void shouldThrowExceptionWhenCouponNotFound() {
        String couponId = UUID.randomUUID().toString();
        when(couponRepository.findById(couponId))
                .thenThrow(new CouponException("Cupom não encontrado."));

        Assertions.assertThatThrownBy(() -> getCoupon.execute(couponId))
                .isInstanceOf(CouponException.class)
                .hasMessage("Cupom não encontrado.");

        verify(couponRepository, times(1)).findById(couponId);
    }

    private CouponResponse couponResponseStub(String couponId){
        LocalDateTime expirationDate = LocalDateTime.of(2025, 12, 31, 23, 59, 59);

        return new CouponResponse(
                couponId,
                "ABC123",
                "Cupom teste",
                expirationDate,
                BigDecimal.valueOf(10),
                CouponStatus.ACTIVE,
                true,
                false
        );
    }
}
