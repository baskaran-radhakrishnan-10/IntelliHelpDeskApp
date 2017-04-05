package com.helpdesk.dao.api.impl;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.persistance_api.hibernate.api.AbstractHibernateDAOAPI;
import com.helpdesk.dao.api.ChatHistoryDAO;
import com.helpdesk.entity.ChatHistory;

public class ChatHistoryDAOImpl implements ChatHistoryDAO{
	
	private AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI;

	public void setAbstractHibernateDAOAPI(AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI) {
		this.abstractHibernateDAOAPI = abstractHibernateDAOAPI;
	}

	@Override
	public int addChatHistory(ChatHistory chatHistory) throws DaoException {
		return abstractHibernateDAOAPI.saveEntity(chatHistory);
	}

	@Override
	public void removeChatHistory(Map<String, Object> restrictionMap) throws DaoException {
		abstractHibernateDAOAPI.deleteEntity(ChatHistory.class, restrictionMap);
	}

	@Override
	public void updateChatHistory(ChatHistory chatHistory) throws DaoException {
		abstractHibernateDAOAPI.updateEntity(chatHistory);
	}

	@Override
	public List<ChatHistory> getChatHistory(Map<String, Object> restrictionMap) throws DaoException {
		return abstractHibernateDAOAPI.getEntityList(ChatHistory.class, restrictionMap);
	}

	@Override
	public List<ChatHistory> listChatHistory(Map<String, Object> restrictionMap) throws DaoException {
		return abstractHibernateDAOAPI.getEntityList(ChatHistory.class, null);
	}

}
