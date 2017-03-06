package com.quotesystem.datacontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;
import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.Question;

@RestController
@RequestMapping("/quote")
public class QuoteRestController {

	@Autowired
	private QuoteRepository quoteRepository;

	/*
	 * Inserts a new quote which has been received at /quote and inserts it into the database
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public Quote submitNewQuote(@RequestBody Quote quote) {
		quote.calculateTotalQuoteValue(); // evaluate quote prior to submission into database
		return quoteRepository.save(quote);
	}

	/*
	 * Retrieves an existing quote to the client at /quote/view/{id}
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public @ResponseBody Quote getExistingQuote(@PathVariable String id) {
		Quote quote = new Quote();
		quote.setUsername("admin");
		ArrayList<Question> entries = new ArrayList<>();
		BooleanQuestion question = new BooleanQuestion();
		question.setPrompt("Are you able to read?");
		question.setValue(500.0);
		question.setValueWeight(1);
		question.setResponse(true);
		entries.add(question);
		quote.setEntries(entries);
		quote.calculateTotalQuoteValue();
		return quote;
	//	return quoteRepository.findById(id);
	}

	/*
	 * Retrieves a list of quotes generated by a specified user at /quote/search/{username}
	 */
	@RequestMapping(value = "/search/{username}", method = RequestMethod.GET)
	public @ResponseBody List<Quote> getQuotesByUsername(@PathVariable String username) {
		return quoteRepository.findByUsername(username);
	}

}
