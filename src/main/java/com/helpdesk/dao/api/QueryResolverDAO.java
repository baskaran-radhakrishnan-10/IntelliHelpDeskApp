package com.helpdesk.dao.api;

import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;

public interface QueryResolverDAO {
	
	public Map<String, Object> getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException;

}
