package com.quotesystem.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	 @RequestMapping(value = "/authenticate")
	public boolean login(){
		return true;
	}
	 

}
