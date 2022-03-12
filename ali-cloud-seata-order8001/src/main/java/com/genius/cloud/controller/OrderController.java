package com.genius.cloud.controller;

import com.genius.cloud.bean.Order;
import com.genius.cloud.common.CommonResult;
import com.genius.cloud.mq.config.JmsConfig;
import com.genius.cloud.mq.producer.ProducerOrder;
import com.genius.cloud.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@Api("订单")
@RefreshScope
@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${config.info}")
    private String configInfo;

    @Resource
    private ProducerOrder producerOrder;

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
        orderService.create(order);
        SendResult send = producerOrder.send(new Message(JmsConfig.TOPIC_ORDER, "order_create", "创建成功".getBytes(StandardCharsets.UTF_8)));
        if (send == null) {
            log.error("MQ消息发送失败");
        }
        return new CommonResult<>(200, "订单创建成功");
    }

    @ApiOperation(value = "订单查询", notes = "订单查询接口")
    @ApiImplicitParams(
        @ApiImplicitParam(name = "id", value = "订单Id", dataType = "Integer")
    )
    @GetMapping("/get")
    public CommonResult<Object> get(Integer id) {
        Order order = orderService.getById(id);
        return new CommonResult<>(200, "订单获取成功", order);
    }

}
