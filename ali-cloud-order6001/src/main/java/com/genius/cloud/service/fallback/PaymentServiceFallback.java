package com.genius.cloud.service.fallback;

import cloud.beans.Payment;
import cloud.common.CommonResult;
import com.genius.cloud.service.PaymentService;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public CommonResult<Payment> paymentSql(Long id) {
        return new CommonResult<>(500, "服务降级返回, PaymentServiceFallback", new Payment(id, "errorSerial"));
    }

}
