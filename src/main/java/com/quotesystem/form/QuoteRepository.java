package com.quotesystem.form;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteRepository extends MongoRepository<Quote, String> { // MongoDB quote data collection which will be autowired

	public Quote findById(String id);

	public Quote findByUsername(String username);

}
