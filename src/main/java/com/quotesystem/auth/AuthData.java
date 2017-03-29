package com.quotesystem.auth;

import java.util.ArrayList;

public class AuthData {
	private String username;
	private ArrayList<String> roles;
	
	public AuthData() {
		
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the roles
	 */
	public ArrayList<String> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
}
