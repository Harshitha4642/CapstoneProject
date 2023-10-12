package com.capstone.cdr.repository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.Type;

public interface TypeRepository extends CrudRepository<Type, Integer> {

	Type findByCallType(String callType);

}
