package com.tddchalenge.tddchalenge.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tddchalenge.tddchalenge.dtos.CityDTO;
import com.tddchalenge.tddchalenge.services.CityService;

@RestController
@RequestMapping(value="/cities")
public class CityResource {
	
	
	@Autowired
	private CityService service;
	/*
	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll(){
		List<CityDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	*/

	@GetMapping
	public ResponseEntity<Page<CityDTO>> findAll(
											@RequestParam(value = "page", defaultValue = "0") Integer page,
											@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
											@RequestParam(value = "direction", defaultValue = "ASC") String direction,
											@RequestParam(value = "orderBy", defaultValue = "name") String orderBy  ){
		
		PageRequest request = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		
		Page<CityDTO> list = service.findAllPaged(request);
		
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<CityDTO> findById(@PathVariable Long id)
	{
		CityDTO list = service.findById(id);
		return ResponseEntity.ok().body(list);
	}	
	
	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@RequestBody CityDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);		
	}

	@PutMapping(value="/{id}")
	public ResponseEntity<CityDTO> update(@PathVariable Long id,@RequestBody CityDTO dto) {
		dto = service.update(id,dto);
		return ResponseEntity.ok().body(dto);		
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();		
	}
	
}
