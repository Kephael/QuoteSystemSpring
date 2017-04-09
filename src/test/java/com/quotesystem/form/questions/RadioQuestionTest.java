package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class RadioQuestionTest {

	RadioQuestion question;
	ArrayList<SelectionOption> optionList;
	SelectionOption option0;
	SelectionOption option1;
	SelectionOption option2;

	@Before
	public void setUp() {
		question = new RadioQuestion();
		optionList = new ArrayList<SelectionOption>();
		option0 = new SelectionOption();
		option1 = new SelectionOption();
		option2 = new SelectionOption();
		optionList.add(option0);
		optionList.add(option1);
		optionList.add(option2);
		question.setValue(optionList);
	}

	@Test
	public void testGetCost() {
		question.setResponse(1);
		option2.setValue(120.0);
		option2.setValueWeight(1.0);
		assertEquals(0.0, question.calculateServiceCost(), 0.0); // choose option with null value
		question.setResponse(2);
		assertEquals(120.0, question.calculateServiceCost(), 0.0); // choose option with set value
		question.setResponse(null);
		assertEquals(0.0, question.calculateServiceCost(), 0.0); // null response test
	}
}
