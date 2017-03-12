package com.quotesystem.form;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quotesystem.form.questions.Question;

@Document
public class Quote {
	@Id
	@JsonIgnore
	private String id; // MongoDB document ID
	private Long identity;  // unique id from which to interact with via REST
	private String description;
	private String username; // user which created quote
	private ArrayList<Question> questions;
	private double totalQuoteValue; // total value of entries in quote

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getTotalQuoteValue() {
		return totalQuoteValue;
	}

	/**
	 * @return the identity
	 */
	public Long getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(Long identity) {
		this.identity = identity;
	}

	/**
	 * calculates the dollar value of the quote
	 */
	public void calculateTotalQuoteValue() {
		double val = 0;
		for (Question question : questions) {
			val += (question.calculateServiceCost());
		}
		this.setTotalQuoteValue(val);
	}

	public void setTotalQuoteValue(double totalQuoteValue) {
		this.totalQuoteValue = totalQuoteValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
}
