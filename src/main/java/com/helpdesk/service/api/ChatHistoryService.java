package com.helpdesk.service.api;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.APIException;
import com.helpdesk.entity.ChatHistory;

public interface ChatHistoryService {
	
	public int addChatHistory(ChatHistory chatHistory) throws APIException;

    public void removeChatHistory(int chatHistoryId) throws APIException;

    public void updateChatHistory(ChatHistory chatHistory) throws APIException;

    public List<ChatHistory> getChatHistory(Map<String,Object> inputParam) throws APIException;
    
    public List<Map<String,Object>> getChatHistoryByDate(Map<String,Object> inputParam) throws APIException;
    
    public List<ChatHistory> listChatHistory() throws APIException;

}
