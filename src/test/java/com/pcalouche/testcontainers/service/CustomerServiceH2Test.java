package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.dto.CustomerDetails;
import com.pcalouche.testcontainers.entity.Customer;
import com.pcalouche.testcontainers.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class CustomerServiceH2Test {
    @Autowired
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    public void beforeEach() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void testFindAll() {
        Assertions.assertThat(customerService.findAll()).hasSize(3);

        Customer customer = Customer.builder()
                .name("Phil Calouche")
                .customerDetails(CustomerDetails.builder().x("x-value").build())
                .build();
        customerRepository.save(customer);

        Assertions.assertThat(customerService.findAll()).hasSize(4);
    }
}
