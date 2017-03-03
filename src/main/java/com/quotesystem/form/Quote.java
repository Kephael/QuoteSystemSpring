package com.quotesystem.form;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Quote {
	@Id
	private String id;
	private String username; // user which created quote
	private ArrayList<QuoteEntry> entries;
	private double totalQuoteValue; // total value of entries in quote

	public double getTotalQuoteValue() {
		return totalQuoteValue;
	}

	/**
	 * @return dollar value of the quote
	 */
	public double calculateTotalQuoteValue() {
		double val = 0;
		for (QuoteEntry quoteEntry : entries) {
			val += (quoteEntry.getValue() * quoteEntry.getValueWeight());
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

	public ArrayList<QuoteEntry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<QuoteEntry> entries) {
		this.entries = entries;
	}
}
