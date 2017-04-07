package com.helpdesk.service.api.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equiniti.exception.api.exception.APIException;
import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.exception.api.faultcode.CommonFaultCode;
import com.helpdesk.dao.api.QueryResolverDAO;
import com.helpdesk.service.api.QueryResolverService;

@Service("queryResolverService")
public class QueryResolverServiceImpl implements QueryResolverService{
	
	@Autowired
	private QueryResolverDAO queryResolverDAO;
	
	@Override
	public String getSolutionForQuery(Map<String,Object> inputParam) throws APIException {
		try {
			//queryResolverDAO.getSolutionForQuery(inputParam);
			return "Work is in progress!!!";
		} catch (Exception e) {
			throw new APIException(CommonFaultCode.UNKNOWN_ERROR, e);
		}
		
	}

}
