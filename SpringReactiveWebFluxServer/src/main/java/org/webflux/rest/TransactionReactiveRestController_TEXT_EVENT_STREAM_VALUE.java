package org.webflux.rest;

import java.awt.geom.FlatteningPathIterator;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.webflux.dao.ISocieteRepository;
import org.webflux.dao.ITransactionRepository;
import org.webflux.domain.Transaction;

import reactor.core.publisher.Flux;

//par defaut le MediaType pour produces est "MediaType.APPLICATION_JSON_VALUE".
// Le control envoi la reponse en un seul bloc contenant toute les donnéees entieres.

// avec MediaType.TEXT_EVENT_STREAM_VALUE les données sont envoiées éléments par éléments
@RestController
public class TransactionReactiveRestController_TEXT_EVENT_STREAM_VALUE {

	@Autowired
	private ITransactionRepository transactionRepository;

	@Autowired
	private ISocieteRepository societeRepository;

	// envoi element par element
	@GetMapping(value = "/streamtransactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Transaction> streamTransactionfindAll() {
		return transactionRepository.findAll();
	}

	// simulation d'un flux de transaction
    @GetMapping(value = "/streamTransactionsBySociete/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> stream(@PathVariable String id){
        return societeRepository.findById(id)
                .flatMapMany(soc->{
           
            Flux<Long> interval=Flux.interval(Duration.ofMillis(1000));

           Flux<Transaction> transactionFlux= Flux.fromStream(Stream.generate(()->{
                Transaction transaction=new Transaction();
                transaction.setInstant(Instant.now());
                transaction.setSociete(soc);
                transaction.setPrice(soc.getPrice()*(1+(Math.random()*12-6)/100));
                return transaction;
                
            }));
            return Flux.zip(interval,transactionFlux)
                    .map(data->{
                        return data.getT2();   // data.getT1()===> interval
                    }).share();
        });
    }

    

    @GetMapping("/test")
    public String test(){
        return Thread.currentThread().getName();
    }
    

}
