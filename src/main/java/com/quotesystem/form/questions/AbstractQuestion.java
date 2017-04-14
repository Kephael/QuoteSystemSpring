/*
 * This abstract class is extended allowing for new question types to be added to the quote system.
 */
package com.quotesystem.form.questions;

public abstract class AbstractQuestion<E, V> implements Question<E, V> {
	private boolean isRequired;
	private String prompt;
	private V value;
	private double valueWeight;
	private E response; // user entry (response)
	private String category;

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
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	@Override
	public double getValueWeight() {
		return this.valueWeight;
	}

	@Override
	public void setValueWeight(double valueWeight) {
		this.valueWeight = valueWeight;
	}

	@Override
	public E getResponse() {
		return this.response;
	}

	@Override
	public void setResponse(E response) {
		this.response = response;
	}


	@Override
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Override
	public abstract double calculateServiceCost();

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

}
