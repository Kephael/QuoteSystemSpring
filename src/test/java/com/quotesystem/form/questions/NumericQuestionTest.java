package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NumericQuestionTest {
	private NumericQuestion question;

	@Before
	public void setup() {
		this.question = new NumericQuestion();
		question.setValueWeight(50.0);
	}

	@Test
	public void calculateServiceCostTest() {
		assertEquals(0.0, question.calculateServiceCost(), 0);
		question.setResponse(1.0);
		assertEquals(50.0, question.calculateServiceCost(), 0);
		question.setResponse(2.5);
		assertEquals(125.0, question.calculateServiceCost(), 0);
	}
}
