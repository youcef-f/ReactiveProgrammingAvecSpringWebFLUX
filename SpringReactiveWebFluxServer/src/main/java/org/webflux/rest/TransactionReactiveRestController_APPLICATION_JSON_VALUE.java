package org.webflux.rest;

import java.time.Instant;

import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.webflux.dao.ISocieteRepository;
import org.webflux.dao.ITransactionRepository;
import org.webflux.domain.Societe;
import org.webflux.domain.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//par defaut le MediaType pour produces est "MediaType.APPLICATION_JSON_VALUE".
// Le control envoi la reponse en un seul bloc contenant toute les donnéees entieres.

@RestController
public class TransactionReactiveRestController_APPLICATION_JSON_VALUE {

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private ISocieteRepository societeRepository;
	
	// envoi tout en seul bloque json
	@GetMapping(value = "/transactions")
	public Flux<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	// envoi tout en seul bloque json
	@GetMapping(value = "/transactions/{transactionId}")
	public Mono<Transaction> getOne(@PathVariable String transactionId) {
		return transactionRepository.findById(transactionId);
	}

	// envoi la reponse en seul bloque json
	@PostMapping(value = "/transactions")
	public Mono<Transaction> save(@RequestBody Transaction transaction) {
		transaction.setInstant(Instant.now());
		return transactionRepository.save(transaction);
	}

	// envoi la reponse en seul bloque json
	@DeleteMapping(value = "/transactions/{transactionId}")
	public Mono<Void> delete(@PathVariable String transactionId) {
		return transactionRepository.deleteById(transactionId);
	}

	// envoi la reponse en seul bloque json
	@PutMapping(value = "/transactions/{transactionId}")
	public Mono<Transaction> update(@RequestBody Transaction transaction, @PathVariable String transactionId) {

		transaction.setId(transactionId);
		return transactionRepository.save(transaction);
	}


	// envoi tout en seul bloque json
	@GetMapping(value = "/transactionsBySociete/{societeId}")
	public Flux<Transaction> findBySociete(@PathVariable String societeId) {
		
		Societe societe = new Societe(); 
		societe.setId(societeId); // sette juste le id je reste n'st pas nécessaire
		
		return transactionRepository.findBySociete(societe); 
		
	}
	
}
