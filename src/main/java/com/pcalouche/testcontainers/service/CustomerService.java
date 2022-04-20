package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.dto.CustomerDetails;
import com.pcalouche.testcontainers.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer update(int id, String name, CustomerDetails customerDetails);
}
