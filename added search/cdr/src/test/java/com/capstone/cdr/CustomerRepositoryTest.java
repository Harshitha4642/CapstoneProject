package com.capstone.cdr;

import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setPhonenumber("1234567890");

        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("John");
        assertThat(savedCustomer.getPhonenumber()).isEqualTo("1234567890");
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setPhonenumber("9876543210");

       
        customerRepository.save(customer);

       
        Customer retrievedCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertThat(retrievedCustomer).isNotNull();
        assertThat(retrievedCustomer.getName()).isEqualTo("Alice");
        assertThat(retrievedCustomer.getPhonenumber()).isEqualTo("9876543210");
    }
}
