package com.quotesystem.controllers.auth;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quotesystem.auth.AuthData;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

	@RequestMapping(method = RequestMethod.POST, value = "/authenticate")
	public @ResponseBody AuthData login() {
		AuthData data = new AuthData();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		data.setUsername(auth.getName());
		ArrayList<String> authoritiesList = new ArrayList<String>();
		for (GrantedAuthority authority : auth.getAuthorities()) {
			authoritiesList.add(authority.getAuthority());
		}
		data.setRoles(authoritiesList);
		return data;
	}

}
