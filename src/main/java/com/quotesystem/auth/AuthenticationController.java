package com.quotesystem.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	@RequestMapping(value = "/authenticate")
	public AuthData login() {
		AuthData data = new AuthData();
		return data;
	}

}
