package com.quotesystem.form;

public class QuoteEntry {
	private String prompt;
	private QuestionType questionType;
	private double value;
	private String response;
	private byte[] screenshot;
	private double valueWeight;

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public byte[] getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(byte[] screenshot) {
		this.screenshot = screenshot;
	}

	public double getValueWeight() {
		return valueWeight;
	}

	public void setValueWeight(double valueWeight) {
		this.valueWeight = valueWeight;
	}

	private enum QuestionType {
		BOOLEAN, RESPONSE, IMAGE, VALUE
	}
}
