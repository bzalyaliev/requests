package com.github.bzalyaliev.requests.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactAppController {
	/**
	 * Copied from https://stackoverflow.com/questions/47689971/how-to-work-with-react-routers-and-spring-boot-controller
	 The RegEx works like this:
	 '/' - matches root
	 '/{x:[\\w\\-]+}' - matches everything up to the second \. Eg. \foo
	 '/{x:^(?!api$).*$}/*{y:[\\w\\-]+} - matches everything that doesn't start with api. Eg. \foo\bar?page=1
	 **/
	@RequestMapping(value = {"/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}"})
	public String getIndex(HttpServletRequest request) {
		return "index.html";
	}
}
