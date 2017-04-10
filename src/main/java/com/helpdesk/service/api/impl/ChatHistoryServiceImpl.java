package com.helpdesk.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.equiniti.exception.api.exception.APIException;
import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.dao.api.ChatHistoryDAO;
import com.helpdesk.entity.ChatHistory;
import com.helpdesk.service.api.ChatHistoryService;
import com.helpdesk.util.CommonUtil;

@Service("chatHistoryService")
public class ChatHistoryServiceImpl implements ChatHistoryService{

	private ObjectMapper objMapper=new ObjectMapper();

	private ChatHistoryDAO chatHistoryDAO;

	private Map<String, Object> restrictionMap;

	public void setChatHistoryDAO(ChatHistoryDAO chatHistoryDAO) {
		this.chatHistoryDAO = chatHistoryDAO;
	}

	@Override
	public int addChatHistory(ChatHistory chatHistory) throws APIException {
		try {
			return chatHistoryDAO.addChatHistory(chatHistory);
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
	}

	@Override
	public void removeChatHistory(int chatHistoryId) throws APIException {
		try {
			restrictionMap = new HashMap<>();
			restrictionMap.put("gkey", chatHistoryId);
			chatHistoryDAO.removeChatHistory(restrictionMap);
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
	}

	@Override
	public void updateChatHistory(ChatHistory chatHistory) throws APIException {
		try {
			chatHistoryDAO.updateChatHistory(chatHistory);
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
	}

	@Override
	public List<ChatHistory> getChatHistory(Map<String,Object> inputParam) throws APIException {
		List<ChatHistory> returnObj=new LinkedList<>();
		try {
			returnObj=chatHistoryDAO.getChatHistory(inputParam);
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
		return returnObj;
	}

	public List<Map<String,Object>> getChatHistoryByDate(Map<String,Object> inputParam) throws APIException {
		try {
			List<Map<String,Object>> resultObj = chatHistoryDAO.getChatHistoryByDate(inputParam);
			return resultObj;
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
	}

	@Override
	public List<ChatHistory> listChatHistory() throws APIException {
		List<ChatHistory> returnObj=null;
		try {
			returnObj=chatHistoryDAO.listChatHistory(null);
		} catch (DaoException e) {
			throw new APIException(e.getFaultCode(), e);
		}
		return returnObj;
	}

}
