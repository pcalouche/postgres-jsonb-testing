package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.entity.Order;
import com.pcalouche.testcontainers.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class OrderServiceH2Test {
    @Autowired
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    public void testFindAll() {
        Assertions.assertThat(orderService.findAll()).hasSize(3);

        Order order = Order.builder()
                .item("Keyboard")
                .build();
        orderRepository.save(order);

        Assertions.assertThat(orderService.findAll()).hasSize(4);
    }
}
