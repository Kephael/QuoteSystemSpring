package com.quotesystem.form.questions;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.quotesystem.form.Quote;

public class QuoteTest {
	Quote quote;
	ArrayList<Question> questions;
	Question question;

	@Before
	public void setUp() throws Exception {
		 this.quote = new Quote();
		 this.questions = new ArrayList<>();
	     this.question = new BooleanQuestion();
	}

	@Test
	public void calculateTotalQuoteValueTest() {
		Quote quote = new Quote();
		quote.setUsername("admin");
		ArrayList<Question> entries = new ArrayList<>();
		BooleanQuestion question = new BooleanQuestion();
		question.setPrompt("Are you able to read?");
		question.setValue(500.0);
		question.setValueWeight(1);
		question.setResponse(true);
		entries.add(question);
		quote.setEntries(entries);
		quote.calculateTotalQuoteValue();
		assertEquals(500.0, quote.calculateTotalQuoteValue(), .1);
	}

}
