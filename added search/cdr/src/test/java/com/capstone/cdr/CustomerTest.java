package com.capstone.cdr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import com.capstone.cdr.entity.Customer;
import com.capstone.cdr.repository.CustomerRepository;

@DataJpaTest
public class CustomerTest {

    @Autowired
    private CustomerRepository customerRepository; 

    @Test
    public void testCustomerEntity() {
      
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setPhonenumber("+123456789");

        
        customerRepository.save(customer);

       
        Customer retrievedCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertNotNull(retrievedCustomer);
        assertEquals("John Doe", retrievedCustomer.getName());
        assertEquals("+123456789", retrievedCustomer.getPhonenumber());
    }
}
