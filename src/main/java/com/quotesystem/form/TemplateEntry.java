package com.quotesystem.form;

import com.quotesystem.form.questions.AbstractQuestion;

public class TemplateEntry<F extends AbstractQuestion>  {
	
	private String prompt; // text to display for question
	private int questionNumber; // position of question in Template
	private F question;
	
	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public F getQuestionType() {
		return question;
	}

	public void setQuestionType(F questionType) {
		this.question = questionType;
	}

}
