package com.genius.cloud.service;

import cloud.beans.Payment;
import cloud.common.CommonResult;
import com.genius.cloud.service.fallback.PaymentServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ali-cloud-payment", fallback = PaymentServiceFallback.class)
public interface PaymentService {

    @GetMapping(value = "/comsumer/sql/{id}")
    CommonResult<Payment> paymentSql(@PathVariable("id") Long id);

}
