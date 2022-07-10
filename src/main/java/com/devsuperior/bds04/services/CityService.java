package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DataBaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public List<CityDTO> findAllCities() {
		// ordered list by "name"
		List<City> listCities = cityRepository.findAll(Sort.by("name"));
		return listCities.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO cityDto) {
		City newCity = new City();
		newCity.setName(cityDto.getName());
		newCity = cityRepository.save(newCity);
		return new CityDTO(newCity);

	}

	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			// custom exception
			throw new ResourceNotFoundException("City with id: " + id + " not found.");
		} catch (DataIntegrityViolationException e) {
			// custom exception
			throw new DataBaseException("Attention! Data Base Integrity Violation!");
		}

	}
}