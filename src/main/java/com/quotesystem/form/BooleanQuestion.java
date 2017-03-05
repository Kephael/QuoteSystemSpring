package com.quotesystem.form;

public class BooleanQuestion implements QuoteEntry {
	private boolean required;
	private String prompt;
	private QuestionType type;
	private double value;
	private double valueWeight;
	private boolean response;
	
	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public String getPrompt() {
		return prompt;
	}

	@Override
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Override
	public QuestionType getQuestionType() {
		return this.type;
	}

	@Override
	public void setQuestionType(QuestionType questionType) {
		this.type = questionType;
		
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
	}

	public boolean getResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	@Override
	public double getValueWeight() {
		return this.valueWeight;
	}

	@Override
	public void setValueWeight(double valueWeight) {
		this.valueWeight = valueWeight;
	}

}
