package com.quotesystem.counter;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> { // MongoDB quote data collection which will be autowired
	public void deleteById(String id);
}
