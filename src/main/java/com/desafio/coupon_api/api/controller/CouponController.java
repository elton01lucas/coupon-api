package com.desafio.coupon_api.api.controller;

import com.desafio.coupon_api.application.usecase.coupon.CreateCoupon;
import com.desafio.coupon_api.application.usecase.coupon.GetCoupon;
import com.desafio.coupon_api.application.usecase.coupon.response.CouponResponse;
import com.desafio.coupon_api.application.usecase.coupon.DeleteCoupon;
import com.desafio.coupon_api.application.usecase.coupon.request.CreateCouponRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CreateCoupon createCoupon;

    private final DeleteCoupon deleteCoupon;

    private final GetCoupon getCoupon;

    public CouponController(CreateCoupon createCoupon, DeleteCoupon deleteCoupon, GetCoupon getCoupon) {
        this.createCoupon = createCoupon;
        this.deleteCoupon = deleteCoupon;
        this.getCoupon = getCoupon;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(createCoupon.execute(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteCoupon.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(getCoupon.execute(id));
    }
}