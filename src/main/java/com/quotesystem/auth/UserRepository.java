package com.quotesystem.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<PanelUser, String> { // MongoDB user data collection which will be autowired

	public PanelUser findByUsername(String username);

}
