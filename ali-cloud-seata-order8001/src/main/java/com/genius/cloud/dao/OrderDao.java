package com.genius.cloud.dao;

import com.genius.cloud.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    // 1 新建订单
    void create(Order order);

    // 2 修改订单状态，从零改为1
    void update(@Param("userId") Long userId, @Param("status") Integer status);

    // 3 获取订单信息
    Order getById(@Param("id") Integer id);

}
