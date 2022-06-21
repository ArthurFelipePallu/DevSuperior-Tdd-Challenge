package com.tddchalenge.tddchalenge.dtos;

import java.io.Serializable;

import com.tddchalenge.tddchalenge.entities.City;

public class CityDTO implements Serializable{
	private static final long serialVersionUID = 1L;


	private Long id;
	private String name;
	
	public CityDTO() {}

	public CityDTO(City c) {
		this.name = c.getName();
	}

	
	public CityDTO(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
