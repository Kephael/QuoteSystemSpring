package com.quotesystem.form;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateRepository extends MongoRepository<Template, String> { // // MongoDB template data collection which will be autowired

	public Template findByIdentity(Long identity);

	public List<Template> findByUsername(String username);
	
	public Long deleteByIdentity(Long identity);
	
	public void deleteByUsername(String username);
	
}
