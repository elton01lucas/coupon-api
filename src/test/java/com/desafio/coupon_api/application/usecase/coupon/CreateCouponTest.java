package com.desafio.coupon_api.application.usecase.coupon;

import com.desafio.coupon_api.application.usecase.coupon.request.CreateCouponRequest;
import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.model.Coupon;
import com.desafio.coupon_api.domain.model.CouponStatus;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateCouponTest {
    private CouponRepository couponRepository;
    private CreateCoupon createCoupon;

    @BeforeEach
    void setUp() {
        couponRepository = mock(CouponRepository.class);
        createCoupon = new CreateCoupon(couponRepository);
    }

    @Test
    void shouldCreateCouponSuccessfully() {
        CreateCouponRequest request = createCouponRequestStub();

        CouponResponse fakeResponse = couponResponseStub(request);

        when(couponRepository.save(any())).thenReturn(fakeResponse);

        CouponResponse response = createCoupon.execute(request);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.code()).isEqualTo("ABC123");
        Assertions.assertThat(response.description()).isEqualTo(request.description());
        Assertions.assertThat(response.discountValue()).isEqualTo(request.discountValue());
        Assertions.assertThat(response.published()).isEqualTo(request.published());

        ArgumentCaptor<Coupon> captor = ArgumentCaptor.forClass(Coupon.class);
        verify(couponRepository, times(1)).save(captor.capture());

        Assertions.assertThat(captor.getValue().code()).isEqualTo("ABC123");
        Assertions.assertThat(captor.getValue().description()).isEqualTo(request.description());
    }

    private CreateCouponRequest createCouponRequestStub(){
       return new CreateCouponRequest(
                "ABC-123",
                "Cupom de teste",
                BigDecimal.valueOf(10),
                LocalDateTime.now().plusDays(10),
                true
        );
    }

    private CouponResponse couponResponseStub(CreateCouponRequest request){
        return new CouponResponse(
                UUID.randomUUID().toString(),
                "ABC123",
                "Cupom de teste",
                request.expirationDate(),
                request.discountValue(),
                CouponStatus.ACTIVE,
                request.published(),
                false
        );
    }
}
