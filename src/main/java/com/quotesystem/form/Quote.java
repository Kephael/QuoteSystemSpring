package com.quotesystem.form;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quotesystem.form.questions.Question;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	@Id
	@JsonIgnore
	private String id; // MongoDB document ID
	private Long identity; // unique id from which to interact with via REST
	private String description;
	private String username; // user which created quote
	private ArrayList<Question> questions;
	private double totalQuoteValue; // total value of entries in quote
	private HashMap<String, Double> categoryMap;

	/**
	 * @return the categoryMap
	 */
	public HashMap<String, Double> getCategoryMap() {
		return categoryMap;
	}

	/**
	 * @param categoryMap
	 *            the categoryMap to set
	 */
	public void setCategoryMap(HashMap<String, Double> categoryMap) {
		this.categoryMap = categoryMap;
	}

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
		this.setCategoryMap(new HashMap<String, Double>());
		double totalQuoteValue = 0;
		if (this.getQuestions() != null) {
			for (Question question : questions) {
				double questionValue = calculateCategoryValue(question);
				totalQuoteValue += questionValue; // add question value to total quote value independent of category.
			}
		}
		this.setTotalQuoteValue(totalQuoteValue);
	}

	/*
	 * calculates category cost for each question
	 * @return returns the value of the question
	 */
	private double calculateCategoryValue(Question question) {
		double questionValue = question.calculateServiceCost();
		String category = question.getCategory();
		if (category == null) { // MongoDB does not support a null key for hashmap in docoument form
			category = "undefined";
		}

		if (categoryMap.containsKey(category)) { // category is already in map
			double updatedCategoryValue = categoryMap.get(category) + questionValue;
			categoryMap.put(category, updatedCategoryValue);
		} else { // new category
			categoryMap.put(category, questionValue);
		}
		return questionValue;
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
