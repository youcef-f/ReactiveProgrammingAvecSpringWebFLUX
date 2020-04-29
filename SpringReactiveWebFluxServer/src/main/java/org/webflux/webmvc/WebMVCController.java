package org.webflux.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.webflux.dao.ISocieteRepository;
import org.webflux.dao.ITransactionRepository;


// utilisation de thymleaf avec reactor.

@Controller
public class WebMVCController {

	@Autowired
	private ISocieteRepository societeRepository;
	@Autowired
	private ITransactionRepository transactionRepository;
	
	@GetMapping ("/index")
	public String index(Model model) {
		model.addAttribute("societes", societeRepository.findAll());
		return "index";
	}
	
}
