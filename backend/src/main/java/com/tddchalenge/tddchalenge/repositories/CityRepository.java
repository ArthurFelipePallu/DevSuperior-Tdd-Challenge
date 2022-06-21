package com.tddchalenge.tddchalenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tddchalenge.tddchalenge.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City,Long>{

}
