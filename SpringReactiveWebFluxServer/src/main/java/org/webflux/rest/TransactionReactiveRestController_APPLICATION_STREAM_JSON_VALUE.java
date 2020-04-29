package org.webflux.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.webflux.domain.Event;

import reactor.core.publisher.Flux;

@RestController
public class TransactionReactiveRestController_APPLICATION_STREAM_JSON_VALUE {

	private String uri = "http://localhost:8089";

	
	// Get Event through WebClient from "Event Server" under MediaType.APPLICATION_STREAM_JSON_VALUE
	// and sent them to browser into MediaType.TEXT_EVENT_STREAM_VALUE
	@GetMapping(value = "/events/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Event> event(@PathVariable String id) {
		WebClient webClient = WebClient.create(uri);  // with APPLICATION_STREAM_JSON_VALUE

		Flux<Event> eventFlux = webClient.get() //
				.uri("/streamEvents/" + id) //
				.retrieve().bodyToFlux(Event.class);  // recupere un Event

		return eventFlux;

	}
	
	
	// Get Value attribut of Event through WebClient from "Event Server" under MediaType.APPLICATION_STREAM_JSON_VALUE
	// and sent them to browser into MediaType.TEXT_EVENT_STREAM_VALUE
	@GetMapping(value = "/eventsValue/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Double> eventValue(@PathVariable String id) {
		WebClient webClient = WebClient.create(uri);   // with APPLICATION_STREAM_JSON_VALUE

		Flux<Double> eventFlux = webClient.get() //
				.uri("/streamEvents/" + id) //
				.retrieve().bodyToFlux(Event.class) //
				.map(data -> data.getValue());  // on veut recupere que la "value"

		return eventFlux;

	}

}
