package com.quotesystem.datacontrollers;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotesystem.QuoteSystemSpring3Application;
import com.quotesystem.counter.CounterRepository;
import com.quotesystem.form.Quote;
import com.quotesystem.form.Template;
import com.quotesystem.form.TemplateRepository;
import com.quotesystem.users.WithUser;
import com.quotesystem.users.WithUserAdmin;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuoteSystemSpring3Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateRestControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	private ObjectMapper mapper;
	@Autowired
	private TemplateRepository repo;
	@Autowired
	private CounterRepository counterRepo;

	@Before
	public void setup() {
		repo.deleteAll();
		repo.deleteByUsername("junit_user");
		repo.deleteByUsername("junit");
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		mapper = new ObjectMapper();
		counterRepo.deleteAll();
	}

	@Test
	@WithUserAdmin
	public void submitNewTemplateTest() throws Exception {
		String templateJson = generateTemplateJson(999999L);
		MvcResult result = mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson))
				.andReturn(); // submit template with no questions
		Template response = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(response.getUsername(), "junit");
	}

	private String generateTemplateJson(long identity) throws Exception {
		Template template = new Template();
		template.setIdentity(identity);
		String templateJson = mapper.writeValueAsString(template);
		return templateJson;
	}

	@Test
	@WithUserAdmin
	public void getTemplateAndDeleteTest() throws Exception {
		String templateJson = generateTemplateJson(999999L);
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		MvcResult result = mvc.perform(get("/template/view/999999")).andReturn();
		Template response = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(response.getUsername(), "junit");
		result = mvc.perform(put("/template/delete/999999")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // one template should be removed
		assertEquals(1, val);
	}

	@Test
	@WithUserAdmin
	public void getTemplatesByUsernameTest() throws Exception {
		String templateJson = generateTemplateJson(999998L);
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		templateJson = generateTemplateJson(999999L);
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		MvcResult result = mvc.perform(get("/template/search/junit")).andReturn();
		ArrayList<Template> templates = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(2, templates.size()); // expect two templates by "junit" to be retrieved
	}

	@Test
	@WithUserAdmin
	public void CounterServiceTemplateTest() throws Exception {
		String templateJson = generateTemplateJson(0);
		MvcResult result;
		for (int i = 0; i < 3; i++) {
			result = mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson))
					.andReturn(); // submit template 0
		}
		result = mvc.perform(get("/template/search/junit")).andReturn();
		ArrayList<Quote> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(2L, templateListResponse.get(2).getIdentity(), 0);
	}

	@Test
	@WithUser
	public void viewAllTemplateAsUserTest() throws Exception {
		submitTemplates();
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Quote> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(3, templateListResponse.size());
	}

	@Test
	@WithUser
	public void viewOnlyUserTemplatesTest() throws Exception {
		submitTemplates();
		Template anotherUserTemplate = new Template();
		anotherUserTemplate.setUsername("junit");
		anotherUserTemplate.setIdentity(934343L);
		repo.save(anotherUserTemplate); // manually saves a template as a different user
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Quote> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(3, templateListResponse.size()); // only the three junit_user Templates should be provided as a response
	}

	@Test
	@WithUserAdmin
	public void ViewAllTemplateTestAsAdmin() throws Exception {
		submitTemplates();
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Quote> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(3, templateListResponse.size());
	}


	private void submitTemplates() throws Exception {
		String templateJson = generateTemplateJson(0);
		for (int i = 0; i < 3; i++) {
			mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)).andReturn(); // submit template 
		}
	}
}
