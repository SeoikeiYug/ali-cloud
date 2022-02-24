package com.genius.cloud.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "订单", description = "订单对象")
public class Order {

    @ApiModelProperty(value = "Id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "用户Id", required = true)
    private Long userId;

    @ApiModelProperty(value = "产品Id", required = true)
    private Long productId;

    @ApiModelProperty(value = "下单数量", required = true)
    private Integer count;

    @ApiModelProperty(value = "金额", required = true)
    private BigDecimal money;

    @ApiModelProperty(value = "订单状态", required = true)
    private Integer status; //订单状态：0：创建中；1：已完结

}
