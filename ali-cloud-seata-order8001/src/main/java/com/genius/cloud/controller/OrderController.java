package com.genius.cloud.controller;

import com.genius.cloud.bean.Order;
import com.genius.cloud.common.CommonResult;
import com.genius.cloud.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "订单创建", notes = "订单创建接口")
    @GetMapping("/create")
    public CommonResult<Object> create(Order order) {
        orderService.create(order);
        return new CommonResult<>(200, "订单创建成功");
    }

}
