package com.tddchalenge.tddchalenge.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tddchalenge.tddchalenge.dtos.CityDTO;
import com.tddchalenge.tddchalenge.entities.City;
import com.tddchalenge.tddchalenge.repositories.CityRepository;
import com.tddchalenge.tddchalenge.services.exceptions.DatabaseException;
import com.tddchalenge.tddchalenge.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	
	@Autowired
	private CityRepository repository;
	

	@Transactional(readOnly=true)
	public List<CityDTO> findAll(){
		List<City> list = repository.findAll();
		return list.stream().map(x->new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public Page<CityDTO> findAllPaged(PageRequest request){
		Page<City> list = repository.findAll(request);
		return list.map(x->new CityDTO(x));
	}
	
	@Transactional(readOnly=true)
	public CityDTO findById(Long id){
		Optional<City> obj = repository.findById(id);
		City c = obj.orElseThrow(()->new ResourceNotFoundException("Entity NOT FOUND"));
		return new CityDTO(c);		
	}
	
	@Transactional(readOnly=true)
	public CityDTO insert(CityDTO dto){
		
		City c = new City();
		c.setName(dto.getName());

		
		c =repository.save(c);
		return new CityDTO(c);
	}
	
	@Transactional
	public CityDTO update(Long id ,CityDTO dto){
		try {		
			City c = repository.getReferenceById(id);
			c.setName(dto.getName());
				
			c = repository.save(c);
			return new CityDTO(c);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	/*Delete não precisa de @Transactional pois se não , não é capaz de detectar exceptions */
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
	}
}

	
	
