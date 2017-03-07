package com.quotesystem.form;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.quotesystem.form.Quote;
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
		assertEquals(quote.getId(), "823s");
	}

	@Test
	public void getEntriesTest() {
		questions.add(question);
		quote.setEntries(questions);
		assertEquals(quote.getEntries(), questions);
	}

	@Test
	public void calculateTotalQuoteValueTest() {
		Quote quote = new Quote();
		ArrayList<Question> entries = new ArrayList<>();
		BooleanQuestion question = new BooleanQuestion();
		question.setPrompt("Are you able to read?");
		question.setValue(500.0);
		question.setValueWeight(1);
		question.setResponse(true);
		entries.add(question);
		quote.setEntries(entries);
		quote.calculateTotalQuoteValue();
		assertEquals(500.0, quote.calculateTotalQuoteValue(), 0.0);
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
