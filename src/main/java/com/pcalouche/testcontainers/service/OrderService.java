package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
}
