package com.quotesystem.form.questions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BooleanQuestionTest {
	private BooleanQuestion question;
	
	@Before
	public void setUp() throws Exception {
		question = new BooleanQuestion();
	}

	@Test
	public void responseTest() {
		question.setResponse(true);
		assertTrue(question.getResponse());
		question.setResponse(false);
		assertFalse(question.getResponse());
	}

}
