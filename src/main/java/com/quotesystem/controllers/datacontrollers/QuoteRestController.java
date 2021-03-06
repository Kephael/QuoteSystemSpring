package com.quotesystem.controllers.datacontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.auth.AuthData;
import com.quotesystem.counter.CounterService;
import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/quote")
public class QuoteRestController {

	@Autowired
	private QuoteRepository quoteRepository;
	@Autowired
	private CounterService counterService;

	/*
	 * Inserts a new quote which has been received at /quote and inserts it into the database
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Quote> submitNewQuote(@RequestBody Quote quote) {
		Quote resQuote = saveQuote(quote);
		return new ResponseEntity<Quote>(resQuote, HttpStatus.OK);
	}

	private Quote saveQuote(Quote quote) {
		quote.calculateTotalQuoteValue(); // evaluate quote prior to submission into database
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		quote.setUsername(auth.getName()); // sets quote username to that of the user logged in
		quote.setIdentity(counterService.getNextSequence("quote"));
		Quote resQuote = quoteRepository.save(quote);
		return resQuote;
	}
	
	@Transactional
	private Quote saveQuoteWithIdentity(Quote quote, Long identity) {
		quote.calculateTotalQuoteValue(); // evaluate quote prior to submission into database
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		quote.setUsername(auth.getName()); // sets quote username to that of the user logged in
		quote.setIdentity(identity);
		quoteRepository.deleteByIdentity(identity);
		Quote resQuote = quoteRepository.save(quote);
		return resQuote;
	}

	/*
	 * Retrieves an existing quote to the client at /quote/view/{id}
	 */
	@RequestMapping(value = "/view/{identity}", method = RequestMethod.GET)
	public ResponseEntity<Quote> getExistingQuote(@PathVariable Long identity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Quote resQuote = quoteRepository.findByIdentity(identity);
		if (resQuote != null && (auth.getName().equals(resQuote.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			return new ResponseEntity<Quote>(resQuote, HttpStatus.OK);
		}
		return new ResponseEntity<Quote>(new Quote(), HttpStatus.BAD_REQUEST); // invalid credentials to view Quote
	}

	/*
	 * Replaces an existing quote to the client at /quote/view/{id}
	 */
	@RequestMapping(value = "/view/{identity}", method = RequestMethod.PUT)
	public ResponseEntity<Quote> replaceExistingQuote(@PathVariable Long identity, @RequestBody Quote quote) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Quote oldQuote = quoteRepository.findByIdentity(identity);
		if (oldQuote != null && (auth.getName().equals(oldQuote.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			Quote newQuoteFromDb =  saveQuoteWithIdentity(quote, identity);
			return new ResponseEntity<Quote>(newQuoteFromDb, HttpStatus.OK);
		}
		return new ResponseEntity<Quote>(new Quote(), HttpStatus.BAD_REQUEST); // invalid replacement request
	}

	/*
	 * Retrieves a list of quotes generated by a specified user at /quote/search/{username}
	 */
	@RequestMapping(value = "/search/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<Quote>> getQuotesByUsername(@PathVariable String username) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals(username)
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN))) {
			List<Quote> resQuotes = quoteRepository.findByUsername(username);
			return new ResponseEntity<List<Quote>>(resQuotes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Quote>>(new ArrayList<Quote>(), HttpStatus.BAD_REQUEST);
	}

	/*
	 * Deletes quotes based on a specific identity, in most cases the identity value will be unique
	 */
	@RequestMapping(value = "/delete/{identity}", method = RequestMethod.PUT)
	public ResponseEntity<Long> deleteExistingQuote(@PathVariable Long identity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Quote desiredQuoteToDelete = quoteRepository.findByIdentity(identity);
		if (desiredQuoteToDelete != null && (auth.getName().equals(desiredQuoteToDelete.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			Long deleteCount = quoteRepository.deleteByIdentity(identity);
			return new ResponseEntity<Long>(deleteCount, HttpStatus.OK);
		}
		return new ResponseEntity<Long>(0L, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @return a list of all quotes if the user is an admin, otherwise returns all quotes made by them
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResponseEntity<List<Quote>> getAllQuotes() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN))) {
			List<Quote> allQuotes = quoteRepository.findAll(); // administrator users can see all quotes
			return new ResponseEntity<List<Quote>>(allQuotes, HttpStatus.OK);
		}
		List<Quote> userQuotes = quoteRepository.findByUsername(auth.getName()); // non-administrators can only see their quotes
		return new ResponseEntity<List<Quote>>(userQuotes, HttpStatus.OK);
	}

}
