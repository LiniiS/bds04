package com.devsuperior.bds04.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.DataBaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public Page<EventDTO> findAllEvents(Pageable pageable) {
		
		//pageable
		
			Page<Event> eventList = eventRepository.findAll(pageable);
			return eventList.map(event -> new EventDTO(event));
	}
	
	
	@Transactional
	public EventDTO insert(EventDTO eventDto) {

		Event newEvent = new Event();
		getEntityFromDto(eventDto, newEvent);
		newEvent = eventRepository.save(newEvent);

		return new EventDTO(newEvent);
	}
	
	
	public void delete(Long id) {

		try {
			eventRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Attention! Data Base Integrity Violation!");
		}

	}

	
	@Transactional
	public EventDTO update(Long id, EventDTO eventDto) {
		try {
			Event eventUpdated = eventRepository.getOne(id);
			getEntityFromDto(eventDto, eventUpdated);
			eventUpdated = eventRepository.save(eventUpdated);

			return new EventDTO(eventUpdated);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Event with id: " + id + " not found!");
		}

	}

	// prepara a entidade
	private void getEntityFromDto(EventDTO eventDto, Event eventUpdated) {
		eventUpdated.setName(eventDto.getName());
		eventUpdated.setCity(new City(eventDto.getCityId(), null));
		eventUpdated.setDate(eventDto.getDate());
		eventUpdated.setUrl(eventDto.getUrl());
	}
}