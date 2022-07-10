package com.devsuperior.bds04.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;

@RestController
@RequestMapping(value="/events")
public class EventController {

	@Autowired
	private EventService eventService;

	
	@GetMapping
	public ResponseEntity<Page<EventDTO>> findAllEvents(Pageable pageable){
		Page<EventDTO> eventsDto = eventService.findAllEvents(pageable);
		return ResponseEntity.ok().body(eventsDto);
	}

	
	@PutMapping(value="/{id}")
	public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDto){
		eventDto = eventService.update(id, eventDto);
		
		return ResponseEntity.ok().body(eventDto);
	}
	
	@PostMapping()
	public ResponseEntity<EventDTO> addNewEvent(@Valid @RequestBody EventDTO eventDto){
		eventDto = eventService.insert(eventDto);
		
		URI newResourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(eventDto.getId()).toUri();
		return ResponseEntity.created(newResourceUri).body(eventDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<EventDTO> deleteCategory(@PathVariable Long id) {
		eventService.delete(id);
		return ResponseEntity.noContent().build();
	}


}