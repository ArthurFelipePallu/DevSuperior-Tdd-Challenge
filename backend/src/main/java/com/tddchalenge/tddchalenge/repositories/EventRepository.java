package com.tddchalenge.tddchalenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tddchalenge.tddchalenge.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>{

}
