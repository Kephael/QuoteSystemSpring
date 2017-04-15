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
import com.quotesystem.form.Template;
import com.quotesystem.form.TemplateRepository;

@RestController
@CrossOrigin(origins = "*")
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
	public ResponseEntity<Template> submitNewTemplate(@RequestBody Template template) {
		Template resTemplate = saveTemplate(template);
		return new ResponseEntity<Template>(resTemplate, HttpStatus.OK);
	}

	private Template saveTemplate(Template template) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		template.setUsername(auth.getName()); // sets template username to that of the user logged in
		template.setIdentity(counterService.getNextSequence("template"));
		Template resTemplate = templateRepository.save(template);
		return resTemplate;
	}

	@Transactional
	private Template deleteOldTemplateandSaveNewTemplateWithIdentity(Template template, Long identity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		template.setUsername(auth.getName()); // sets template username to that of the user logged in
		template.setIdentity(identity);
		templateRepository.deleteByIdentity(identity); // delete old template
		Template resTemplate = templateRepository.save(template); // save replacement template
		return resTemplate;
	}

	/*
	 * @return an existing template to the client at /template/view/{id}
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/view/{identity}")
	public ResponseEntity<Template> getExistingTemplate(@PathVariable Long identity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Template resTemplate = templateRepository.findByIdentity(identity);
		if (resTemplate != null && (auth.getName().equals(resTemplate.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			return new ResponseEntity<Template>(resTemplate, HttpStatus.OK);
		}
		return new ResponseEntity<Template>(new Template(), HttpStatus.BAD_REQUEST); // invalid credentials to view template
	}

	/*
	 * Replaces an existing template at /template/view/{id}
	 * @return returns new template to the client
	 */
	@RequestMapping(value = "/view/{identity}", method = RequestMethod.PUT)
	public ResponseEntity<Template> replaceExistingTemplate(@PathVariable Long identity,
			@RequestBody Template template) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Template oldTemplate = templateRepository.findByIdentity(identity);
		if (oldTemplate != null && (auth.getName().equals(oldTemplate.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			Template newTemplateFromDb = deleteOldTemplateandSaveNewTemplateWithIdentity(template, identity);
			return new ResponseEntity<Template>(newTemplateFromDb, HttpStatus.OK);
		}
		return new ResponseEntity<Template>(new Template(), HttpStatus.BAD_REQUEST); // invalid replacement request
	}

	/*
	 * @return a list of templates created by a certain username
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/search/{username}")
	public ResponseEntity<List<Template>> getTemplatesByUsername(@PathVariable String username) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals(username)
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN))) {
			List<Template> resTemplates = templateRepository.findByUsername(username);
			return new ResponseEntity<List<Template>>(resTemplates, HttpStatus.OK);
		}
		return new ResponseEntity<List<Template>>(new ArrayList<Template>(), HttpStatus.BAD_REQUEST);
	}

	/*
	 * @return the number of templates deleted (should be 1 if identity was valid)
	 */
	@RequestMapping(value = "/delete/{identity}", method = RequestMethod.PUT)
	public ResponseEntity<Long> deleteExistingTemplate(@PathVariable Long identity) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Template desiredTemplateToDelete = templateRepository.findByIdentity(identity);
		if (desiredTemplateToDelete != null && (auth.getName().equals(desiredTemplateToDelete.getUsername())
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN)))) {
			Long deleteCount = templateRepository.deleteByIdentity(identity);
			return new ResponseEntity<Long>(deleteCount, HttpStatus.OK);
		}
		return new ResponseEntity<Long>(0L, HttpStatus.BAD_REQUEST);
	}

	/*
	 * @return a list of all templates regardless of role
	  */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResponseEntity<List<Template>> getAllTemplates() {
		List<Template> allTemplates = templateRepository.findAll(); // all users can see all templates
		return new ResponseEntity<List<Template>>(allTemplates, HttpStatus.OK);
	}
}
