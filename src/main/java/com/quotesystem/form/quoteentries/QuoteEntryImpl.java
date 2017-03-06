/*
 * This abstract class is extended allowing for new question types to be added to the quote system.
 */
package com.quotesystem.form.quoteentries;

import com.quotesystem.form.QuestionType;

public abstract class QuoteEntryImpl<E, V> implements QuoteEntry<E, V> {
	private boolean isRequired;
	private String prompt;
	private QuestionType type;
	private V value;
	private double valueWeight;
	private E response;
	
	@Override
	public boolean isRequired() {
		return isRequired;
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
		return type;
	}

	@Override
	public void setQuestionType(QuestionType questionType) {
		this.type = questionType;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public double getValueWeight() {
		return this.getValueWeight();
	}

	@Override
	public void setValueWeight(double valueWeight) {
		this.valueWeight = valueWeight;
	}

	@Override
	public  E getResponse() {
		return this.response;
	}

	@Override
	public void setResponse(E response) {
		this.response = response;
	}

	public abstract double getCost();

}
