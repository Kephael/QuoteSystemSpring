package com.quotesystem.controllers.datacontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.auth.AuthData;
import com.quotesystem.counter.CounterService;
import com.quotesystem.form.Quote;
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		template.setUsername(auth.getName()); // sets template username to that of the user logged in
		if (template.getIdentity() == 0) {
			template.setIdentity(counterService.getNextSequence("template"));
		}
		Template resTemplate = templateRepository.save(template);
		return new ResponseEntity<Template>(resTemplate, HttpStatus.OK);
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
	 * @return a list of templates created by a certain username
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/search/{username}")
	public ResponseEntity<List<Template>> getTemplatesByUsername(@PathVariable String username) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName().equals(username) || auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN))) {
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
	 * @return a list of all quotes if the user is an admin, otherwise returns all quotes made by them
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ResponseEntity<List<Template>> getAllQuotes() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(AuthData.ADMIN))) {
			List<Template> allTemplates = templateRepository.findAll(); // administrator users can see all quotes
			return new ResponseEntity<List<Template>>(allTemplates, HttpStatus.OK);
		}
		List<Template> userQuotes = templateRepository.findByUsername(auth.getName()); // non-administrators can only see their quotes
		return new ResponseEntity<List<Template>>(userQuotes, HttpStatus.OK);
	}

}
