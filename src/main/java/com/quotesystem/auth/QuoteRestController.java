package com.quotesystem.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;

@RestController
@RequestMapping("/quote")
public class QuoteRestController {

	@Autowired
	private QuoteRepository quoteRepository;

	/*
	 * Inserts a new quote which has been received at /quote and inserts it into the NoSQL database
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public Quote submitNewQuote(@RequestBody Quote quote) {
		return quoteRepository.save(quote);
	}

	/*
	 * Returns a new quote to the client at /quote/{id}
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Quote getExistingQuote(@PathVariable String id) {
		System.out.println("REQUEST");
		return quoteRepository.findById(id);
	}

}
