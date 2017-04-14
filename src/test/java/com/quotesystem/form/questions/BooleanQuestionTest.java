package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BooleanQuestionTest {
	private BooleanQuestion question;

	@Before
	public void setUp() {
		question = new BooleanQuestion();
	}

	@Test
	public void responseTest() {
		question.setResponse(true);
		assertTrue(question.getResponse());
		question.setResponse(false);
		assertFalse(question.getResponse());
	}

	@Test
	public void getServiceCostTest() {
		question.setResponse(false);
		assertEquals(0.0, question.calculateServiceCost(), 0.0);
		question.setResponse(true);
		question.setValue(150.0);
		question.setValueWeight(2);
		assertEquals(300.0, question.calculateServiceCost(), 0.0);
		question.setResponse(null);
		assertEquals(0.0, question.calculateServiceCost(), 0.0);
	}

}
