package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.dto.CustomerDetails;
import com.pcalouche.testcontainers.entity.Customer;
import com.pcalouche.testcontainers.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(int id, String name, CustomerDetails customerDetails) {
        Customer existingCustomer = customerRepository.getById(id);
        existingCustomer.setName(name);
        existingCustomer.setCustomerDetails(customerDetails);
        return customerRepository.save(existingCustomer);
    }
}
