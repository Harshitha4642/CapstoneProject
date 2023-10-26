package com.capstone.cdr.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.MessageCDR;

public interface MessageCDRRepository extends CrudRepository<MessageCDR, Integer> {

    @Query(value = "select * from messagecdr where subscriber = ?1 or reciever = ?1 ", nativeQuery = true)
    List<MessageCDR> findAllByCustomerId(int id);

    @Query(value="select * from messagecdr where subscriber_location = ?1 or reciever_location = ?1", nativeQuery = true)
    List<MessageCDR> findAllByLocation(String content);

    List<MessageCDR> findAllByDate(LocalDate localDate);

}
