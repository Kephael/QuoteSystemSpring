package com.quotesystem.datacontrollers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotesystem.QuoteSystemSpring3Application;
import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;
import com.quotesystem.form.questions.AbstractQuestion;
import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.LongResponseQuestion;
import com.quotesystem.form.questions.Question;
import com.quotesystem.form.questions.RadioQuestion;

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
	@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
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
	@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
	public void submitEvaluateAndDeleteTest() throws Exception {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		RadioQuestion radio = new RadioQuestion();
		BooleanQuestion question1 = new BooleanQuestion();
		question1.setResponse(true);
		question1.setValue(500.0);
		question1.setValueWeight(1.0);
		ArrayList<AbstractQuestion> radioOptions = new ArrayList<AbstractQuestion>();
		radio.setValue(radioOptions);
		radio.setResponse(0);
		questions.add(radio);
		quote.setQuestions(questions);
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
	@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
	public void removeNoneTest() throws Exception {
		MvcResult result = mvc.perform(put("/quote/delete/955006")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // zero documents should be removed
		assertEquals(0, val);
	}

	@Test
	@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
	public void findQuotesByUserAndCheckQuoteValueTest() throws Exception {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		RadioQuestion radio = new RadioQuestion();
		BooleanQuestion question1 = new BooleanQuestion();
		question1.setResponse(true);
		question1.setValue(500.0);
		question1.setValueWeight(1.0);
		ArrayList<AbstractQuestion> radioOptions = new ArrayList<AbstractQuestion>();
		radio.setValue(radioOptions);
		radio.setResponse(0);
		questions.add(radio);
		quote.setQuestions(questions);
		quote.setUsername("junit");
		quote.setIdentity(955006L);
		radioOptions.add(question1);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote with Radio question
		quote.setIdentity(955007L);
		result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson)).andReturn(); // submit quote with Radio question
		result = mvc.perform(get("/quote/search/junit")).andReturn();
		ArrayList<Quote> quoteListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				 mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(2, quoteListResponse.size()); // two quotes by the "JUnit" user should be returned
		assertEquals(500.0, quoteListResponse.get(0).getTotalQuoteValue(), 0.0); // first quote should have a value of 500
	}
	
	@Test
	@WithMockUser(username = "junit", roles = { "USER", "ADMIN" })
	public void getJson() throws Exception{
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		RadioQuestion radio = new RadioQuestion();
		BooleanQuestion question1 = new BooleanQuestion();
		question1.setResponse(true);
		question1.setValue(500.0);
		question1.setValueWeight(1.0);
		LongResponseQuestion question2 = new LongResponseQuestion();
		question2.setPrompt("This is a test prompt, can you see this?");
		question2.setRequired(true);
		question2.setResponse("Yes, I can see this.");
		ArrayList<AbstractQuestion> radioOptions = new ArrayList<AbstractQuestion>();
		radio.setValue(radioOptions);
		radio.setResponse(0);
		questions.add(radio);
		questions.add(question2);
		quote.setQuestions(questions);
		quote.setUsername("junit");
		quote.setIdentity(955006L);
		radioOptions.add(question1);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote with Radio question
		quote.setIdentity(955007L);
		result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson)).andReturn(); // submit quote with Radio question
		result = mvc.perform(get("/quote/search/junit")).andReturn();
		ArrayList<Quote> quoteListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				 mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(2, quoteListResponse.size()); // two quotes by the "JUnit" user should be returned
	}

}
