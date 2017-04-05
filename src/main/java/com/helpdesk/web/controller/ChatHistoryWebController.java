package com.helpdesk.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.equiniti.exception.api.exception.ControllerException;
import com.equiniti.exception.api.exception.UIException;
import com.helpdesk.controller.ChatHistoryController;

@Controller
@RequestMapping(value="chat")
public class ChatHistoryWebController {

	private static final Logger LOG= Logger.getLogger(ChatHistoryWebController.class); 

	@Autowired
	private ChatHistoryController chatHistoryController;

	@RequestMapping(value = "/showChatHistory")
	public String showChatHistory(){
		return "chatpage";
	}

	@RequestMapping(value = "/getChatHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getChatHistory(@RequestBody Map<String,Object> inputData) throws UIException{
		LOG.debug("START getChatHistory() Method!!!");
		Map<String,Object> returnObj=new HashMap<>();
		try {
			returnObj = chatHistoryController.getChatHistory(inputData);	
		} catch (ControllerException e) {
			throw new UIException(e.getFaultCode(), e);
		}
		LOG.debug("END getChatHistory() Method!!!");
		return returnObj;
	}

	@RequestMapping(value = "/addChatHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addChatHistory(@RequestBody Map<String,Object> inputData) throws UIException{
		LOG.debug("START addChatHistory() Method!!!");
		Map<String,Object> returnObj=new HashMap<>();
		try {
			returnObj=chatHistoryController.addChatHistory(inputData);
		} catch (ControllerException e) {
			throw new UIException(e.getFaultCode(), e);
		}
		LOG.debug("END addChatHistory() Method!!!");
		return returnObj;
	}

	@RequestMapping(value = "/updateChatHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateChatHistory(@RequestBody Map<String,Object> inputData) throws UIException{
		LOG.debug("START updateChatHistory() Method!!!");
		Map<String,Object> returnObj=new HashMap<>();
		try {
			returnObj=chatHistoryController.updateChatHistory(inputData);
		} catch (ControllerException e) {
			throw new UIException(e.getFaultCode(), e);
		}
		LOG.debug("END updateChatHistory() Method!!!");
		return returnObj;
	}

	@RequestMapping(value = "/deleteChatHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteChatHistory(@RequestBody Map<String,Object> inputData) throws UIException{
		LOG.debug("START deleteChatHistory() Method!!!");
		Map<String,Object> returnObj=new HashMap<>();
		try {
			returnObj=chatHistoryController.deleteChatHistory(inputData);
		} catch (ControllerException e) {
			throw new UIException(e.getFaultCode(), e);
		}
		LOG.debug("END deleteChatHistory() Method!!!");
		return returnObj;
	}

}
