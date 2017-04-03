package com.helpdesk.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.equiniti.exception.api.exception.APIException;
import com.helpdesk.form.model.LoginModelAttribute;
import com.helpdesk.service.api.LoginService;
import com.helpdesk.util.ApplicationConstants;

@Service("loginController")
public class LoginController {

	private static final Logger LOG=Logger.getLogger(LoginController.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private LoginService loginService;
	
	//private CacheInstance CACHE_INS=null;

	public String doLogout() throws CacheException{
		//CACHE_INS=CacheInstance.getInstance();
		HttpSession session=request.getSession();
		//CACHE_INS.removeAllItemFromGroup((String) session.getAttribute(ApplicationConstants.USER_ID));
		Enumeration<String> sessionAttributeList=session.getAttributeNames();
		while(sessionAttributeList.hasMoreElements()){
			String attrName=sessionAttributeList.nextElement();
			session.removeAttribute(attrName);
		}
		session.invalidate();
		return ApplicationConstants.REDIRECT_LOGIN_PAGE;
	}

	public String doLogin(LoginModelAttribute modelAttribute){

		LOG.debug("<----- START doLogin Method ----->");
		
		Model model=modelAttribute.getModel();

		String userId=modelAttribute.getUserId().trim();

		String passwordText=modelAttribute.getPassword().trim();

		if(null == userId || userId.isEmpty()){
			model.addAttribute("error", "Enter valid user id");
			return ApplicationConstants.LOGIN_PAGE;
		}

		if(null == passwordText || passwordText.isEmpty()){
			model.addAttribute("error", "Enter valid password");
			return ApplicationConstants.LOGIN_PAGE;
		}
		
		try {
			modelAttribute=loginService.doLogin(modelAttribute);
		} catch (APIException e) {
			e.printStackTrace();
		}
		
		if(!modelAttribute.isSuccess()){
			return modelAttribute.getResultMapping();
		}
		
		LOG.debug("<----- END doLogin Method ----->");
		
		return ApplicationConstants.REDIRECT_HOME_PAGE;

	}

}
