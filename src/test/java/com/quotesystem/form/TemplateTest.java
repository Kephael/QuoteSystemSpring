package com.quotesystem.form;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.quotesystem.form.questions.BooleanQuestion;
import com.quotesystem.form.questions.Question;

public class TemplateTest {
	Template template;
	ArrayList<Question> questions;
	Question question;

	@Before
	public void setUp() {
		this.template = new Template();
		this.questions = new ArrayList<>();
		this.question = new BooleanQuestion();
	}

	@Test
	public void idTest() {
		template.setId("dsfsd9w34");
		assertEquals("dsfsd9w34", template.getId());
	}
	
	@Test
	public void identityTest(){
		template.setIdentity(9440L);
		assertEquals(new Long(9440L), template.getIdentity());
	}
	
	@Test
	public void descriptionTest(){
		template.setDescription("This is a description.");
		assertEquals("This is a description.", template.getDescription());
	}
	
	@Test
	public void usernameTest(){
		template.setUsername("junit");
		assertEquals("junit", template.getUsername());
	}
	
	@Test
	public void questionsTest(){
		template.setEntries(questions);
		assertEquals(questions, template.getQuestions());
	}

}
