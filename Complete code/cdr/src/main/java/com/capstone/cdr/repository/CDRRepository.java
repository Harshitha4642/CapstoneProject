package com.capstone.cdr.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.CDR;

public interface CDRRepository extends CrudRepository<CDR, Integer> {

    @Query(value = "select * from cdr where subscriber = ?1 or reciever = ?1 ", nativeQuery = true)
    List<CDR> findAllByCustomerId(int i);

    @Query(value="select * from cdr where subscriber_location = ?1 or reciever_location = ?1", nativeQuery = true)
    List<CDR> findAllByLocation(String content);

    List<CDR> findAllByDate(LocalDate localDate);

}
