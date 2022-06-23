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

import com.tddchalenge.tddchalenge.dtos.EventDTO;
import com.tddchalenge.tddchalenge.entities.Event;
import com.tddchalenge.tddchalenge.repositories.EventRepository;
import com.tddchalenge.tddchalenge.services.exceptions.DatabaseException;
import com.tddchalenge.tddchalenge.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;

	@Transactional(readOnly=true)
	public List<EventDTO> findAll(){
		List<Event> list = repository.findAll();
		return list.stream().map(x->new EventDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public Page<EventDTO> findAllPaged(PageRequest request){
		Page<Event> list = repository.findAll(request);
		return list.map(x->new EventDTO(x));
	}
	
	@Transactional(readOnly=true)
	public EventDTO findById(Long id){
		Optional<Event> obj = repository.findById(id);
		Event c = obj.orElseThrow(()->new ResourceNotFoundException("Entity NOT FOUND"));
		return new EventDTO(c);		
	}
	
	@Transactional(readOnly=true)
	public EventDTO insert(EventDTO dto){
		
		Event e = new Event();
		e.setName(dto.getName());
		e.setDate(dto.getDate());
		e.setUrl(dto.getUrl());
		
		e =repository.save(e);
		return new EventDTO(e);
	}
	
	@Transactional
	public EventDTO update(Long id ,EventDTO dto){
		try {		
			Event e = repository.getReferenceById(id);
			e.setName(dto.getName());
			e.setDate(dto.getDate());
			e.setUrl(dto.getUrl());			
			e =repository.save(e);
			return new EventDTO(e);
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
