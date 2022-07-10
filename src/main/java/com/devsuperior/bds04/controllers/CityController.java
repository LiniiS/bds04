package com.devsuperior.bds04.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;

@RestController
@RequestMapping(value="/cities")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@GetMapping
	public ResponseEntity<List<CityDTO>> findAllCities(){
		List<CityDTO> listCities = cityService.findAllCities();
		return ResponseEntity.ok().body(listCities);
	}

	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO cityDto){
		cityDto = cityService.insert(cityDto);
		
		URI newResourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cityDto.getId()).toUri();
		return ResponseEntity.created(newResourceUri).body(cityDto);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<CityDTO> deleteCity(@PathVariable Long id){
		//service realiza a verificaçaõ de id existente/inexistente/dependente
		cityService.delete(id);
		//retorna no content (204) se tudo der certo
		return ResponseEntity.noContent().build();
	}
}
