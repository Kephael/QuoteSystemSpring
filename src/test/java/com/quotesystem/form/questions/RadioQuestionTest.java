package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RadioQuestionTest {
	RadioQuestion question;
	ArrayList<AbstractQuestion> radioList;
	BooleanQuestion question0;
	LongResponseQuestion question1;
	BooleanQuestion question2;

	@Before
	public void setUp() {
		question = new RadioQuestion();
		radioList = new ArrayList<AbstractQuestion>();
		question0 = new BooleanQuestion();
		question1 = new LongResponseQuestion();
		question2 = new BooleanQuestion();
		radioList.add(question0);
		radioList.add(question1);
		radioList.add(question2);
		question.setValue(radioList);
	}

	@Test
	public void testGetCost() {
		question.setResponse(2);
		question2.setResponse(true);
		question2.setValue(120.0);
		question2.setValueWeight(1.0);
		assertEquals(120.0, question.calculateServiceCost(), 0.0);
		question.setResponse(null);
		assertEquals(0.0, question.calculateServiceCost(), 0.0);
	}

}
