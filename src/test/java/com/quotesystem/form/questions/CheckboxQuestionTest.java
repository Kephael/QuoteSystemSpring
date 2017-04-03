package com.quotesystem.form.questions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class CheckboxQuestionTest {
	CheckboxQuestion question;
	ArrayList<SelectionOption> optionList;
	SelectionOption option0;
	SelectionOption option1;
	SelectionOption option2;
	ArrayList<Integer> userSelections;

	@Before
	public void setUp() {
		question = new CheckboxQuestion();
		optionList = new ArrayList<SelectionOption>();
		option0 = new SelectionOption();
		option1 = new SelectionOption();
		option2 = new SelectionOption();
		optionList.add(option0);
		optionList.add(option1);
		optionList.add(option2);
		question.setValue(optionList);
		userSelections = new ArrayList<Integer>();
	}

	@Test
	public void testGetCost() {
		userSelections.add(2);
		question.setResponse(userSelections);
		option2.setValue(120.0);
		option2.setValueWeight(1.0);
		assertEquals(120.0, question.calculateServiceCost(), 0.0);
		question.setResponse(null);
		assertEquals(0.0, question.calculateServiceCost(), 0.0);
	}

}
