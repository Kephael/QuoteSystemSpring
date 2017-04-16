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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotesystem.QuoteSystemSpring3Application;
import com.quotesystem.counter.CounterRepository;
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
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		mapper = new ObjectMapper();
		counterRepo.deleteAll();
		repo.deleteAll();
	}

	@Test
	@WithUserAdmin
	public void submitNewTemplateTest() throws Exception {
		String templateJson = generateTemplateJson();
		MvcResult result = mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson))
				.andReturn(); // submit template with no questions
		Template response = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(response.getUsername(), "junit");
	}

	private String generateTemplateJson() throws Exception {
		Template template = new Template();
		String templateJson = mapper.writeValueAsString(template);
		return templateJson;
	}

	@Test
	@WithUserAdmin
	public void getTemplateAndDeleteTest() throws Exception {
		String templateJson = generateTemplateJson();
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		MvcResult result = mvc.perform(get("/template/view/0")).andReturn();
		Template response = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(response.getUsername(), "junit");
		result = mvc.perform(put("/template/delete/0")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // one template should be removed
		assertEquals(1, val);
	}

	@Test
	@WithUserAdmin
	public void getTemplatesByUsernameTest() throws Exception {
		String templateJson = generateTemplateJson();
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		templateJson = generateTemplateJson();
		mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)); // submit template with no questions
		MvcResult result = mvc.perform(get("/template/search/junit")).andReturn();
		ArrayList<Template> templates = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(2, templates.size()); // expect two templates by "junit" to be retrieved
	}

	@Test
	@WithUserAdmin
	public void CounterServiceTemplateTest() throws Exception {
		String templateJson = generateTemplateJson();
		MvcResult result;
		for (int i = 0; i < 3; i++) {
			result = mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson))
					.andReturn(); // submit template 0
		}
		result = mvc.perform(get("/template/search/junit")).andReturn();
		ArrayList<Template> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(2L, templateListResponse.get(2).getIdentity(), 0);
	}

	@Test
	@WithUser
	public void viewAllTemplateAsUserTest() throws Exception {
		submitTemplates();
		saveTemplateManually("junit_fake_user");
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Template> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(4, templateListResponse.size()); 
	}

	@Test
	@WithUser
	public void viewOnlyUserTemplatesTest() throws Exception {
		submitTemplates();
		saveTemplateManually("junit");
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Template> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(4, templateListResponse.size()); //  the three junit_user Templates should be provided as a response and one "junit" template
	}

	@Test
	@WithUserAdmin
	public void ViewAllTemplateTestAsAdmin() throws Exception {
		submitTemplates();
		MvcResult result = mvc.perform(get("/template/view")).andReturn();
		ArrayList<Template> templateListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Template.class));
		assertEquals(3, templateListResponse.size());
	}

	@Test
	@WithUser
	public void deleteInvalidTemplateTest() throws Exception {
		MvcResult result = mvc.perform(put("/template/delete/999991")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); 
		assertEquals(0, val); // no template should be removed
		saveTemplateManually("junit");
		result = mvc.perform(put("/template/delete/934343")).andReturn();
		val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // no template should be removed as it belongs to a different user
		assertEquals(0, val);
	}

	@Test
	@WithUser
	public void viewInvalidTemplateTest() throws Exception {
		saveTemplateManually("junit");
		MvcResult result = mvc.perform(get("/template/view/934343")).andReturn(); // attempt to retrieve template made by another user
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		result = mvc.perform(get("/template/view/999987")).andReturn(); // attempt to view nonexistent template
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}

	@Test
	@WithUser
	public void viewAnotherUsersTemplatesAsUserTest() throws Exception {
		saveTemplateManually("junit_fake_user");
		MvcResult result = mvc.perform(get("/template/search/junit_fake_user")).andReturn(); // attempt to retrieve template made by another user
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		result = mvc.perform(get("/template/search/junit_nonexistant_user")).andReturn(); // attempt to retrieve template made by another user
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithUserAdmin
	public void replaceTemplateTest() throws Exception {
		Template template = new Template();
		String templateJson = mapper.writeValueAsString(template);
		saveTemplateManually("junit_fake_user");
		MvcResult result = mvc.perform(put("/template/view/934343").contentType(MediaType.APPLICATION_JSON).content(templateJson))
				.andReturn(); // attempt to replace another user's template
		Template responseTemplate = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(template.getDescription(), responseTemplate.getDescription());
		result = mvc.perform(put("/template/view/999999").contentType(MediaType.APPLICATION_JSON).content(templateJson))
				.andReturn(); // attempt to replace nonexistent template
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithUserAdmin
	public void replaceAndViewTemplateTest() throws Exception {
		submitTemplates();
		Template template = new Template();
		template.setDescription("test message");
		String TemplateJson = mapper.writeValueAsString(template);
		MvcResult result = mvc.perform(put("/template/view/1").contentType(MediaType.APPLICATION_JSON).content(TemplateJson))
				.andReturn(); // attempt to replace own template
		Template responseTemplate = mapper.readValue(result.getResponse().getContentAsString(), Template.class); 
		assertEquals(template.getDescription(), responseTemplate.getDescription());
		result = mvc.perform(get("/template/view/1")).andReturn(); // attempt to retrieve template that was replaced
		Template response = mapper.readValue(result.getResponse().getContentAsString(), Template.class);
		assertEquals(template.getDescription(), response.getDescription()); 
	}
	
	
	private void saveTemplateManually(String username) {
		Template anotherUserTemplate = new Template();
		anotherUserTemplate.setUsername(username);
		anotherUserTemplate.setIdentity(934343L);
		repo.save(anotherUserTemplate);
	}

	/*
	 * submits three templates to the REST /template endpoint
	 */
	private void submitTemplates() throws Exception {
		String templateJson = generateTemplateJson();
		for (int i = 0; i < 3; i++) {
			mvc.perform(post("/template").contentType(MediaType.APPLICATION_JSON).content(templateJson)).andReturn(); // submit template 
		}
	}
}
