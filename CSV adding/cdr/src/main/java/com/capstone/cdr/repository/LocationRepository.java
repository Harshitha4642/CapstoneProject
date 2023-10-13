package com.capstone.cdr.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.capstone.cdr.entity.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {

	Optional<Location> findByLocationName(String subscriberLocation);

}
