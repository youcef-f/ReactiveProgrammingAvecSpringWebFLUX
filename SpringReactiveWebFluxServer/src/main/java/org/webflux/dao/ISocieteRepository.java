package org.webflux.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.webflux.domain.Societe;

public interface ISocieteRepository extends ReactiveMongoRepository<Societe,String> {

}
