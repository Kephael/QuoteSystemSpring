package com.quotesystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.form.Quote;
import com.quotesystem.form.Template;
import com.quotesystem.form.TemplateRepository;

@RestController
@RequestMapping("/template")
public class TemplateRestController {
	
	@Autowired
	TemplateRepository templateRepository;
	
	/*
	 * Inserts a new template which has been received at /template and inserts it into the database
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json" )
	public Template submitNewTemplate(@RequestBody Template template){
		return templateRepository.save(template);
	}
	
	/*
	 * Returns an existing template to the client at /template/{id}
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{/view/id}" )
	public @ResponseBody Template getExistingTemplate(@PathVariable String id) {
		return templateRepository.findById(id);
	}


}
