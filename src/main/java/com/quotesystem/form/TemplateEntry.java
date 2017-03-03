package com.quotesystem.form;

public class TemplateEntry {
	
	private String prompt; // text to display for question
	private QuestionType questionType; // type of question
	private int questionNumber; // position of question in Template
	
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

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}


	private enum QuestionType {
		BOOLEAN, RESPONSE, IMAGE, VALUE
	}
}
