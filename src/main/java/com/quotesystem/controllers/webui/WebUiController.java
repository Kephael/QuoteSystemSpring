package com.quotesystem.controllers.webui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebUiController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/index.html")
	public String index() {
		return "/app/index.html";
	}
}