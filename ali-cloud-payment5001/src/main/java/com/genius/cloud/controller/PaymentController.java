package com.genius.cloud.controller;

import cloud.beans.Payment;
import cloud.common.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    private static Map<Long, Payment> data = new HashMap<>();

    static {
        data.put(1L, new Payment(1L, "0001"));
        data.put(2L, new Payment(2L, "0001"));
        data.put(3L, new Payment(3L, "0001"));
    }

    @GetMapping(value = "/nacos/{id}")
    public String getPayment(@PathVariable Integer id) {
        return "nacos registry, serverPort: " + serverPort + "\t id" + id;
    }

    @GetMapping(value = "/sql/{id}")
    public CommonResult<Payment> getPaymentSql(@PathVariable Long id) {
        Payment payment = data.get(id);
        return new CommonResult<>(200, "from mysql,serverPort:  " + serverPort, payment);
    }
}
