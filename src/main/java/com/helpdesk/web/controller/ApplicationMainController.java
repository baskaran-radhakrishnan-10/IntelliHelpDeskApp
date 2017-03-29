package com.helpdesk.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.helpdesk.form.model.LoginModelAttribute;


@Controller
public class ApplicationMainController {
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String rootApplicationPath(HttpServletRequest request){
		request.setAttribute("loginModelAttribute", new LoginModelAttribute());
		return "loginpage";
	}

}
