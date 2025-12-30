package com.desafio.coupon_api.infrastructure.persistence.repository;

import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.domain.exception.CouponException;
import com.desafio.coupon_api.domain.model.Coupon;
import com.desafio.coupon_api.domain.model.CouponStatus;
import com.desafio.coupon_api.domain.repository.CouponRepository;
import com.desafio.coupon_api.infrastructure.persistence.entity.CouponEntity;
import com.desafio.coupon_api.infrastructure.persistence.jpa.CouponJpaRepository;
import com.desafio.coupon_api.infrastructure.persistence.mapper.CouponMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class CouponRepositoryImplTest {

    private CouponRepositoryImpl couponRepository;
    private CouponMapper mapper;
    private CouponJpaRepository jpaRepository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(CouponJpaRepository.class);
        mapper = new CouponMapper();
        couponRepository = new CouponRepositoryImpl(jpaRepository, mapper);
    }

    @Test
    void shouldSaveCouponSuccessfully() {
        Coupon coupon = couponStub();

        when(jpaRepository.save(any())).thenAnswer(invocation -> {
            CouponEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID()); // simula ID gerado pelo banco
            return entity;
        });

        CouponResponse saved = couponRepository.save(coupon);

        Assertions.assertThat(saved.id()).isNotNull();
        Assertions.assertThat(saved.code()).isEqualTo("ABC123");
        Assertions.assertThat(saved.status()).isEqualTo(CouponStatus.ACTIVE);
    }

    @Test
    void shouldGetCouponSuccessfully() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = couponEntityStub(id);

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        CouponResponse finded = couponRepository.findById(id.toString());

        Assertions.assertThat(finded.id()).isEqualTo(id.toString());
        Assertions.assertThat(finded.code()).isEqualTo("ABC123");
        Assertions.assertThat(finded.status()).isEqualTo(CouponStatus.ACTIVE);
    }


    @Test
    void shouldDeleteCouponSuccessfully() {
        UUID id = UUID.randomUUID();
        CouponEntity entity = couponEntityStub(id);

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(jpaRepository.save(entity)).thenAnswer(i -> i.getArguments()[0]);

        couponRepository.delete(id.toString());

        Assertions.assertThat(entity.getStatus()).isEqualTo(CouponStatus.DELETED);
        verify(jpaRepository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenCouponNotFound() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> couponRepository.delete(id.toString()))
                .isInstanceOf(CouponException.class)
                .hasMessage("Cupom não encontrado.");
    }

    private Coupon couponStub(){
        return Coupon.create(
                "ABC-123",
                "Cupom teste",
                BigDecimal.valueOf(10),
                LocalDateTime.now().plusDays(10),
                true
        );
    }

    private CouponEntity couponEntityStub(UUID id){
        CouponEntity entity = new CouponEntity();
        entity.setId(id);
        entity.setCode("ABC123");
        entity.setDescription("Cupom teste");
        entity.setDiscountValue(BigDecimal.TEN);
        entity.setExpirationDate(LocalDateTime.now().plusDays(5));
        entity.setPublished(true);
        entity.setStatus(CouponStatus.ACTIVE);
        return entity;
    }

}
