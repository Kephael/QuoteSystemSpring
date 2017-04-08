package com.quotesystem.controllers.datacontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.counter.CounterService;
import com.quotesystem.form.Template;
import com.quotesystem.form.TemplateRepository;

@RestController
@RequestMapping("/template")
public class TemplateRestController {

	@Autowired
	TemplateRepository templateRepository;
	@Autowired
	private CounterService counterService;

	/*
	 * Inserts a new template which has been received at /template and inserts it into the database
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public Template submitNewTemplate(@RequestBody Template template) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		template.setUsername(auth.getName()); // sets template username to that of the user logged in
		if (template.getIdentity() == 0) {
			template.setIdentity(counterService.getNextSequence("template"));
		}
		return templateRepository.save(template);
	}

	/*
	 * @return an existing template to the client at /template/view/{id}
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/view/{identity}")
	public @ResponseBody Template getExistingTemplate(@PathVariable Long identity) {
		return templateRepository.findByIdentity(identity);
	}

	/*
	 * @return a list of templates created by a certain username
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/search/{username}")
	public @ResponseBody List<Template> getTemplatesByUsername(@PathVariable String username) {
		return templateRepository.findByUsername(username);
	}

	/*
	 * @return the number of templates deleted (should be 1 if identity was valid)
	 */
	@RequestMapping(value = "/delete/{identity}", method = RequestMethod.PUT)
	public @ResponseBody Long deleteExistingTemplate(@PathVariable Long identity) {
		return templateRepository.deleteByIdentity(identity);
	}

}
