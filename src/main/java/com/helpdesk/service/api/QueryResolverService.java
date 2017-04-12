package com.helpdesk.service.api;

import java.util.Map;

import com.equiniti.exception.api.exception.APIException;

public interface QueryResolverService {
	
	public Map<String, Object> getSolutionForQuery(Map<String,Object> inputParam) throws APIException;

}
