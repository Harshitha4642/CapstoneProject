package com.capstone.cdr.repository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
