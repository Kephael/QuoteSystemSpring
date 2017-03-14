package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LongResponseQuestionTest {
	private LongResponseQuestion question;
	
	@Before
	public void setup(){
		this.question = new LongResponseQuestion();
	}
	
	@Test
	public void calculateServiceCostTest(){
		assertEquals(0.0, question.calculateServiceCost(), 0);
	}
	
	
}
