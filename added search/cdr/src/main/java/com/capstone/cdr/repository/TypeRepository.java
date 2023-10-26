package com.capstone.cdr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.Type;

public interface TypeRepository extends CrudRepository<Type, Integer> {

	Type findByType(String callType);
	
	@Query(value="select * from type call_type", nativeQuery = true)
	List<Type> findDifferentTypes();

}
