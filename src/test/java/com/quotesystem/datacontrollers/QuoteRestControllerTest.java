package com.quotesystem.datacontrollers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotesystem.QuoteSystemSpring3Application;
import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;
import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.Question;
import com.quotesystem.form.questions.QuestionImpl;
import com.quotesystem.form.questions.RadioQuestion;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuoteSystemSpring3Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	private ObjectMapper mapper;
	@Autowired
	private QuoteRepository repo;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		mapper = new ObjectMapper();
		repo.deleteByUsername("junit"); // delete any junit entries

	}

	@Test
	public void invalidLoginTest() {
		String body = restTemplate.getForObject("/authenticate", String.class);
		assertTrue(body.contains("Unauthorized"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void validLoginTest() throws Exception {
		MvcResult result = mvc.perform(get("/authenticate")).andReturn();
		assertTrue(result.getResponse().getContentAsString().contains("true"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void submitAndFindAndDeleteQuoteTest() throws Exception {
		Quote quote = new Quote();
		quote.setUsername("junit");
		quote.setIdentity(955005L);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote with no questions
		Quote response = mapper.readValue(result.getResponse().getContentAsString(), Quote.class);
		assertEquals(response.getUsername(), "junit");
		result = mvc.perform(get("/quote/view/955005")).andReturn(); // get quote from data store
		response = mapper.readValue(result.getResponse().getContentAsString(), Quote.class);
		assertEquals(response.getUsername(), "junit");
		result = mvc.perform(put("/quote/delete/955005")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // one document should be removed
		assertEquals(1, val);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void submitEvaluateAndDeleteTest() throws Exception {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		RadioQuestion radio = new RadioQuestion();
		BooleanQuestion question1 = new BooleanQuestion();
		question1.setResponse(true);
		question1.setValue(500.0);
		question1.setValueWeight(1.0);
		ArrayList<QuestionImpl> radioOptions = new ArrayList<QuestionImpl>();
		radio.setValue(radioOptions);
		radio.setResponse(0);
		questions.add(radio);
		quote.setEntries(questions);
		quote.setIdentity(955006L);
		radioOptions.add(question1);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote with Radio question
		result = mvc.perform(put("/quote/delete/955006")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // one documents should be removed
		assertEquals(1, val);

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void removeNone() throws Exception {
		MvcResult result = mvc.perform(put("/quote/delete/955006")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // zero documents should be removed
		assertEquals(0, val);
	}

}
