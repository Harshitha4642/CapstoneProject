package com.capstone.cdr.repository;

import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.MessageType;

public interface MessageTypeRepository extends CrudRepository<MessageType, Integer> {

	MessageType findByType(String type);

}
