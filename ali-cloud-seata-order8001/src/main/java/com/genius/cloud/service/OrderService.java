package com.genius.cloud.service;

import com.genius.cloud.bean.Order;

public interface OrderService {

    void create(Order order);

    Order getById(Integer id);

}
