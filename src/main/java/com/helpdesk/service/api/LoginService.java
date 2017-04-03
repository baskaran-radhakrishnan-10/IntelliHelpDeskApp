package com.helpdesk.service.api;

import com.equiniti.exception.api.exception.APIException;
import com.helpdesk.form.model.LoginModelAttribute;

public interface LoginService {
	
	public LoginModelAttribute doLogin(LoginModelAttribute modelAttribute) throws  APIException;

}
