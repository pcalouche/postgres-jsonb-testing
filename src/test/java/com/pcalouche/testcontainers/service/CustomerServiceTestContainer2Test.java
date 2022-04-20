package com.pcalouche.testcontainers.service;

import com.pcalouche.testcontainers.dto.CustomerDetails;
import com.pcalouche.testcontainers.entity.Customer;
import com.pcalouche.testcontainers.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerServiceTestContainer2Test {
    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:9.6.24")
            .withDatabaseName("postgres")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    public void beforeEach() {
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void testUpdate() {
        Customer customer = customerRepository.findAll().get(0);

        String newName = "New Name";
        CustomerDetails newCustomerDetails = CustomerDetails.builder().x("new x value").build();

        customerService.update(customer.getId(), newName, newCustomerDetails);

        Assertions.assertThat(customerRepository.findAll()).hasSize(3);
        Customer updatedCustomer = customerRepository.getById(customer.getId());
        assertThat(updatedCustomer.getName()).isEqualTo(newName);
        Assertions.assertThat(updatedCustomer.getCustomerDetails()).isEqualTo(newCustomerDetails);
    }
}
