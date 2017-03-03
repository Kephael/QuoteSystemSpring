package com.quotesystem.form;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteRepository extends MongoRepository<Quote, String> { // MongoDB quote data collection which will be autowired

	public Quote findById(String id);

	public List<Quote> findByUsername(String username);

}
