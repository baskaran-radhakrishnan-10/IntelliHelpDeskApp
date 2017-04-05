package com.helpdesk.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equiniti.exception.api.exception.APIException;
import com.equiniti.exception.api.exception.ControllerException;
import com.equiniti.exception.api.faultcode.CommonFaultCode;
import com.helpdesk.entity.ChatHistory;
import com.helpdesk.entity.User;
import com.helpdesk.service.api.ChatHistoryService;
import com.helpdesk.util.ApplicationConstants;
import com.helpdesk.util.CommonUtil;

@Component("chatHistoryController")
public class ChatHistoryController {
	
	private ObjectMapper objMapper=new ObjectMapper();
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ChatHistoryService chatHistoryService;
	
	public Map<String,Object> getChatHistory(Map<String,Object> inputParam) throws ControllerException{
		Map<String,Object> returnObjMap=new HashMap<>();
		try {
			Object returnObj=chatHistoryService.getChatHistory(inputParam);
			returnObjMap.put(ApplicationConstants.SERVER_DATA, returnObj);
		} catch (APIException e) {
			throw new ControllerException(e.getFaultCode(), e);
		} catch(Exception e){
			throw new ControllerException(CommonFaultCode.UNKNOWN_ERROR, e);
		}
		return returnObjMap;
	}
	
	public Map<String,Object> addChatHistory(Map<String,Object> inputParam) throws ControllerException{
		Map<String,Object> returnObjMap=new HashMap<>();
		try {
			Object returnObj=chatHistoryService.addChatHistory(populateEntityFromMap(inputParam));
			returnObjMap.put(ApplicationConstants.SERVER_DATA, returnObj);
			returnObjMap.put(ApplicationConstants.STATUS, ApplicationConstants.SUCCESS);
		} catch (APIException e) {
			throw new ControllerException(e.getFaultCode(), e);
		} catch(Exception e){
			throw new ControllerException(CommonFaultCode.UNKNOWN_ERROR, e);
		}
		return returnObjMap;
	}
	
	public Map<String,Object> updateChatHistory(Map<String,Object> inputParam) throws ControllerException{
		Map<String,Object> returnObjMap=new HashMap<>();
		try {
			chatHistoryService.updateChatHistory(populateEntityFromMap(inputParam));
			returnObjMap.put(ApplicationConstants.STATUS, ApplicationConstants.SUCCESS);
		} catch (APIException e) {
			throw new ControllerException(e.getFaultCode(), e);
		} catch(Exception e){
			throw new ControllerException(CommonFaultCode.UNKNOWN_ERROR, e);
		}
		return returnObjMap;
	}
	
	public Map<String,Object> deleteChatHistory(Map<String,Object> paramMap) throws ControllerException{
		Map<String,Object> returnObjMap=new HashMap<>();
		try {
			chatHistoryService.removeChatHistory(Integer.parseInt(paramMap.get("gkey").toString()));
			returnObjMap.put(ApplicationConstants.STATUS, ApplicationConstants.SUCCESS);
		} catch (APIException e) {
			throw new ControllerException(e.getFaultCode(), e);
		} catch(Exception e){
			throw new ControllerException(CommonFaultCode.UNKNOWN_ERROR, e);
		}
		return returnObjMap;
	}
	
	private ChatHistory populateEntityFromMap(Map<String,Object> mapObject){
		ChatHistory entity=objMapper.convertValue(CommonUtil.removeTransientObject(mapObject), ChatHistory.class);
		if(null != entity){
			if(null == entity.getUser()){
				User user = (User) session.getAttribute(ApplicationConstants.USER_OBJ);
				entity.setUser(user);
			}
		}
		return entity;
	}

}