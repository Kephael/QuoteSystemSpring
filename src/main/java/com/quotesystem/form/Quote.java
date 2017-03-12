package com.quotesystem.form;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quotesystem.form.questions.Question;
import com.quotesystem.form.questions.QuestionImpl;

@Document
public class Quote {
	@Id
	@JsonIgnore
	private String id; // MongoDB document ID
	private Long identity;
	private String description;

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

	private String username; // user which created quote
	private ArrayList<Question> entries;
	private double totalQuoteValue; // total value of entries in quote

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
	 * @return dollar value of the quote
	 */
	public double calculateTotalQuoteValue() {
		double val = 0;
		for (Question question : entries) {
			val += (question.getServiceCost());
		}
		return val;
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

	public ArrayList<Question> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<Question> questions) {
		this.entries = questions;
	}
}
