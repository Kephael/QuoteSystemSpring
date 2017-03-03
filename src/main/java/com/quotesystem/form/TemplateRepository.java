package com.quotesystem.form;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateRepository extends MongoRepository<Template, String> { // 

	public Template findById(String id);

	public Template findByUsername(String username);

	public List<Template> findByDescriptionLike(String description);
}
