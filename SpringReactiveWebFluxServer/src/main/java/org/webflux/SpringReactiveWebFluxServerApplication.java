package org.webflux;

import java.time.Instant;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.webflux.dao.ISocieteRepository;
import org.webflux.dao.ITransactionRepository;
import org.webflux.domain.Societe;
import org.webflux.domain.Transaction;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringReactiveWebFluxServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveWebFluxServerApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ISocieteRepository societeRepository, ITransactionRepository transactionRepository) {

		return args -> {

			//////////////////////////////// ajouter une societe
			//////////////////////////////// ////////////////////////////////////////////////////
			// 1param: ??? , 2param: error = null, 3param: Complete = ajouterSociete
			societeRepository.deleteAll().subscribe(null, null, () -> { // Delete all societe Asynchrone.

				ajouterSociete(societeRepository);

			});

			/////////////////////////////// ajouter transaction pour chaque societe
			/////////////////////////////// //////////////////////////////////

			transactionRepository.deleteAll().subscribe(null, null, () -> { // Suppression de toutes les transactions.
				Stream.of("SocieteGeneral", "BnpParisBas", "AXA").forEach(scte -> {
					societeRepository.findById(scte).subscribe(sc -> {
						ajouteTransaction(sc, transactionRepository); // ajoute les nouvelles transactions
					});
				});
			});

			System.out.println(
					"Ce message s'affiche avant meme que la suppression et l'ajout de socete et transaction qu'ils soient finis");
		};
	}

	private void ajouterSociete(ISocieteRepository societeRepository) {

		Stream.of("SocieteGeneral", "BnpParisBas", "AXA").forEach(scte -> {
			societeRepository.save(new Societe(scte, scte, 100 + Math.random() * 900)) // add new societe
					.subscribe( //
							respSociete -> { //
								System.out.println(respSociete.toString());//
							});//
		});
	}

	private void ajouteTransaction(Societe societe, ITransactionRepository transactionRepository) {
		for (int i = 0; i < 10; i++) {

			Transaction transaction = new Transaction();

			transaction.setInstant(Instant.now());
			transaction.setPrice(societe.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
			transaction.setSociete(societe);

			transactionRepository.save(transaction).subscribe(trsc -> {
				System.out.println(trsc.toString());
			});
		}
	}

}
