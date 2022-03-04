package com.genius.cloud.controller;

import com.genius.cloud.bean.Order;
import com.genius.cloud.common.CommonResult;
import com.genius.cloud.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api("订单")
@RefreshScope
@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${config.info}")
    private String configInfo;

    @Resource
    private OrderService orderService;

    @GetMapping("/info")
    public CommonResult<Object> info() {
        log.info("配置信息： {}", configInfo);
        return new CommonResult<>(200, "配置信息： " + configInfo);
    }

    @ApiOperation(value = "订单创建", notes = "订单创建接口")
    @GetMapping("/create")
    public CommonResult<Object> create(Order order) {
        log.info("订单创建，配置信息： {}", configInfo);
        orderService.create(order);
        return new CommonResult<>(200, "订单创建成功");
    }

}
