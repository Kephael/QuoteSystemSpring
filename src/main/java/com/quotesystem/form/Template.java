package com.quotesystem.form;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quotesystem.form.questions.Question;

@Document
public class Template {
	@Id
	@JsonIgnore
	private String id; // MongoDB document id
	private String username; // user which created template
	private String description; // description which is used to search for LIKE documents
	private ArrayList<Question> entries;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description of the template
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	public void setEntries(ArrayList<Question> entries) {
		this.entries = entries;
	}
}