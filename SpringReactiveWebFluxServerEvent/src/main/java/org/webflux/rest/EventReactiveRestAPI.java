package org.webflux.rest;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.webflux.domain.Event;

import lombok.Data;
import reactor.core.publisher.Flux;

@RestController
public class EventReactiveRestAPI {

	
	@GetMapping(value="/streamEvents/{id}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Event> listenvents(@PathVariable String id){
		Flux<Long> interval = Flux.interval(Duration.ofMillis(1000)) ;
		Flux<Event> events = Flux.fromStream(Stream.generate(() -> {
			
			Event event = new Event();
			event.setInstant(Instant.now());
			event.setSocieteId(id);
			event.setValue(100+Math.random()*1000);
			return event ; 
		} )) ;
		
		
		return Flux.zip(interval, events).map(data -> { 
			return data.getT2(); // Event 
		}) ; 
	}
	
}




