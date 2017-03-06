package com.quotesystem.form;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.quotesystem.form.quoteentries.QuoteEntry;

@Document
public class Template {
	@Id
	private String id;
	private String username; // user which created template
	private String description; // description which is used to search for LIKE documents
	private ArrayList<QuoteEntry> entries;

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