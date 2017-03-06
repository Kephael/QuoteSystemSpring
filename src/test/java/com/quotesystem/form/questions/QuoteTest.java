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
	public void test() {
		question.setPrompt("This is a test");
		question.setValue(500);
		question.setResponse(true);
		question.setValueWeight(1);
		questions.add(question);
	//	quote.setEntries(questions);
	}

}
