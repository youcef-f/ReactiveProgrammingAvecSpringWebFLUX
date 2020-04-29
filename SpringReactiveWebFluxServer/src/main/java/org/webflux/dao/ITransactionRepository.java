package org.webflux.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.webflux.domain.Societe;
import org.webflux.domain.Transaction;

import reactor.core.publisher.Flux;

public interface ITransactionRepository extends  ReactiveMongoRepository<Transaction,String> {

	Flux<Transaction> findBySociete(Societe societe);

}
