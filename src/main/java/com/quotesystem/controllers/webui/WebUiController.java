package com.quotesystem.controllers.webui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebUiController {
	
	@RequestMapping("/")
	public String index() {
		return "/app/index.html";
	}
}