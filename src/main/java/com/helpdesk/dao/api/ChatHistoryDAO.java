package com.helpdesk.dao.api;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.entity.ChatHistory;

public interface ChatHistoryDAO {
	
	public int addChatHistory(ChatHistory chatHistory) throws DaoException;

    public void removeChatHistory(Map<String, Object> restrictionMap) throws DaoException;

    public void updateChatHistory(ChatHistory chatHistory) throws DaoException;

    public List<ChatHistory> getChatHistory(Map<String, Object> restrictionMap) throws DaoException;
    
    public List<ChatHistory> listChatHistory(Map<String, Object> restrictionMap) throws DaoException;
    
    public List<Map<String,Object>> getChatHistoryByDate(Map<String,Object> restrictionMap) throws DaoException;

}
