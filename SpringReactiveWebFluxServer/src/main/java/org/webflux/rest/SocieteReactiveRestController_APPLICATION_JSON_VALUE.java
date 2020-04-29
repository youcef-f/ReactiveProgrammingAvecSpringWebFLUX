package org.webflux.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.webflux.dao.ISocieteRepository;
import org.webflux.domain.Societe;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


//par defaut le MediaType pour produces est "MediaType.APPLICATION_JSON_VALUE".
//Le control envoi la reponse en un seul bloc contenant toute les donnéees entieres.

@RestController
public class SocieteReactiveRestController_APPLICATION_JSON_VALUE {

	@Autowired
	private ISocieteRepository societeRepository;

	// envoi tout en seul bloque json
	@GetMapping(value = "/societes")
	public Flux<Societe> findAll() {
		return societeRepository.findAll();
	}

	// envoi tout en seul bloque json
	@GetMapping(value = "/societes/{societeId}")
	public Mono<Societe> getOne(@PathVariable String societeId) {
		return societeRepository.findById(societeId);
	}

	// envoi la reponse en seul bloque json
	@PostMapping(value = "/societes")
	public Mono<Societe> save(@RequestBody Societe societe) {
		return societeRepository.save(societe);
	}

	// envoi la reponse en seul bloque json
	@DeleteMapping(value = "/societes/{societeId}")
	public Mono<Void> delete(@PathVariable String societeId) {
		return societeRepository.deleteById(societeId);
	}

	// envoi la reponse en seul bloque json
	@PutMapping(value = "/societes/{societeId}")
	public Mono<Societe> update(@RequestBody Societe societe,@PathVariable String societeId) {
		
		societe.setId(societeId);
		return societeRepository.save(societe);
	}

	
}
