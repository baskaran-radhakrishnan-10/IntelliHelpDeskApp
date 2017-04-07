package com.helpdesk.dao.api;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.entity.ChatHistory;

public interface QueryResolverDAO {
	
	public List<ChatHistory> getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException;

}
