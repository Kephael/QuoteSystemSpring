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
import com.quotesystem.counter.CounterRepository;
import com.quotesystem.form.Quote;
import com.quotesystem.form.QuoteRepository;
import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.CheckboxQuestion;
import com.quotesystem.form.questions.LongResponseQuestion;
import com.quotesystem.form.questions.Question;
import com.quotesystem.form.questions.SelectionOption;
import com.quotesystem.users.WithUserAdmin;

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
	@Autowired
	private CounterRepository counterRepo;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		mapper = new ObjectMapper();
		repo.deleteByUsername("junit"); // delete any junit entries
		counterRepo.deleteAll();
	}

	@Test
	@WithUserAdmin
	public void submitAndFindAndDeleteQuoteTest() throws Exception {
		Quote quote = new Quote();
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
	@WithUserAdmin
	public void submitEvaluateAndDeleteTest() throws Exception {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		CheckboxQuestion radio = new CheckboxQuestion();
		ArrayList<SelectionOption> radioOptions = new ArrayList<SelectionOption>();
		radio.setValue(radioOptions);
		ArrayList<Integer> userSelections = new ArrayList<Integer>();
		userSelections.add(0);
		radio.setResponse(userSelections);
		questions.add(radio);
		quote.setQuestions(questions);
		quote.setIdentity(955006L);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote with Radio question
		result = mvc.perform(put("/quote/delete/955006")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // one documents should be removed
		assertEquals(1, val);
	}

	@Test
	@WithUserAdmin
	public void removeNoneTest() throws Exception {
		MvcResult result = mvc.perform(put("/quote/delete/955006")).andReturn();
		long val = mapper.readValue(result.getResponse().getContentAsString(), Long.class); // zero documents should be removed
		assertEquals(0, val);
	}

	@Test
	@WithUserAdmin
	public void findQuotesByUserAndCheckQuoteValueTest() throws Exception {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		CheckboxQuestion radio = new CheckboxQuestion();
		ArrayList<SelectionOption> radioOptions = new ArrayList<SelectionOption>();
		ArrayList<Integer> userSelections = new ArrayList<Integer>();
		userSelections.add(0);
		userSelections.add(1);
		radio.setValue(radioOptions);
		SelectionOption option1 = new SelectionOption();
		option1.setPrompt("Question 1 is a test");
		option1.setValue(250.0);
		option1.setValueWeight(1.5);
		SelectionOption option2 = new SelectionOption();
		option2.setPrompt("This is still a test for question 1");
		option2.setValue(250.0);
		option2.setValueWeight(0.5);
		radioOptions.add(option1);
		radioOptions.add(option2);
		radio.setResponse(userSelections);
		questions.add(radio);
		quote.setQuestions(questions);
		quote.setIdentity(955006L);
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
	@WithUserAdmin
	public void getJson() throws Exception {
		Quote quote = createQuote(955006);
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

	@Test
	@WithUserAdmin
	public void CounterServiceQuoteTest() throws Exception {
		Quote quote = createQuote(0);
		String quoteJson = mapper.writeValueAsString(quote);
		MvcResult result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson))
				.andReturn(); // submit quote 0
		result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson)).andReturn(); // submit quote 1
		result = mvc.perform(post("/quote").contentType(MediaType.APPLICATION_JSON).content(quoteJson)).andReturn(); // submit quote 2
		result = mvc.perform(get("/quote/search/junit")).andReturn();
		ArrayList<Quote> quoteListResponse = mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructCollectionType(ArrayList.class, Quote.class));
		assertEquals(2L, quoteListResponse.get(2).getIdentity(), 0);
	}

	private Quote createQuote(long identity) {
		Quote quote = new Quote();
		ArrayList<Question> questions = new ArrayList<>();
		CheckboxQuestion radio = new CheckboxQuestion();
		BooleanQuestion question1 = new BooleanQuestion();
		question1.setResponse(true);
		question1.setValue(500.0);
		question1.setValueWeight(1.0);
		LongResponseQuestion question2 = new LongResponseQuestion();
		question2.setPrompt("This is a test prompt, can you see this?");
		question2.setRequired(true);
		question2.setResponse("Yes, I can see this.");
		ArrayList<SelectionOption> radioOptions = new ArrayList<SelectionOption>();
		radio.setValue(radioOptions);
		ArrayList<Integer> userSelections = new ArrayList<Integer>();
		userSelections.add(0);
		radio.setResponse(userSelections);
		questions.add(radio);
		questions.add(question2);
		quote.setQuestions(questions);
		quote.setIdentity(identity);
		return quote;
	}

}
