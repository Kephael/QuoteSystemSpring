package com.quotesystem.form;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.Question;

public class QuoteTest {
	Quote quote;
	ArrayList<Question> questions;
	Question question;

	@Before
	public void setUp() {
		this.quote = new Quote();
		this.questions = new ArrayList<>();
		this.question = new BooleanQuestion();
	}

	@Test
	public void idTest() {
		quote.setId("823s");
		assertEquals("823s", quote.getId());
	}

	@Test
	public void getEntriesTest() {
		questions.add(question);
		quote.setQuestions(questions);
		assertEquals(questions, quote.getQuestions());
	}

	@Test
	public void calculateTotalQuoteValueTest() {
		Quote quote = new Quote();
		ArrayList<Question> entries = new ArrayList<Question>();
		BooleanQuestion question = new BooleanQuestion();
		question.setPrompt("Are you able to read?");
		question.setValue(500.0);
		question.setValueWeight(1);
		question.setResponse(true);
		entries.add(question);
		quote.setQuestions(entries);
		quote.calculateTotalQuoteValue();
		assertEquals(500.0, quote.getTotalQuoteValue(), 0.0);
	}

	@Test
	public void setTotalQuoteValueTest() {
		quote.setTotalQuoteValue(150.0);
		assertEquals(150.0, quote.getTotalQuoteValue(), 0.0);
	}
	
	@Test
	public void usernameTest(){
		quote.setUsername("test_user");
		assertEquals(quote.getUsername(), "test_user");
	}

}
