package com.capstone.cdr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByName(String content);
    Customer findByPhonenumber(String content);

    @Query(value="select name from customer", nativeQuery = true)
    List<String> findAllNames();

    @Query(value="select phonenumber from customer", nativeQuery = true)
    List<String> findAllNumbers();
    
    @Query(value = "select * from customer where name = ?1 and phonenumber = ?2", nativeQuery = true)
    Customer findByNameAndPhonenumber(String sub_name, String sub_number);

}
