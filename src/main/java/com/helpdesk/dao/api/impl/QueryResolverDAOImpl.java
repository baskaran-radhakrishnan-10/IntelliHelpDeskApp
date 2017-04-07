package com.helpdesk.dao.api.impl;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.persistance_api.hibernate.api.AbstractHibernateDAOAPI;
import com.helpdesk.dao.api.QueryResolverDAO;
import com.helpdesk.entity.ChatHistory;

public class QueryResolverDAOImpl implements QueryResolverDAO{
	
	private AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI;

	public void setAbstractHibernateDAOAPI(AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI) {
		this.abstractHibernateDAOAPI = abstractHibernateDAOAPI;
	}
	
	@Override
	public List<ChatHistory> getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException {
		return abstractHibernateDAOAPI.getEntityList(ChatHistory.class, restrictionMap);
	}

}
