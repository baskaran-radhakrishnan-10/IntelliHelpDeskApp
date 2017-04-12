package com.helpdesk.dao.api.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.exception.api.faultcode.CommonFaultCode;
import com.equiniti.persistance_api.consenum.QueryOperationType;
import com.equiniti.persistance_api.consenum.QueryType;
import com.equiniti.persistance_api.hibernate.api.AbstractHibernateDAOAPI;
import com.helpdesk.dao.api.ChatHistoryDAO;
import com.helpdesk.entity.ChatHistory;
import com.helpdesk.util.DateAndTimeUtil;

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
	
	@Override
	public ChatHistory getChatHistoryByKey(Map<String, Object> restrictionMap) throws DaoException{
		return abstractHibernateDAOAPI.getEntity(ChatHistory.class, restrictionMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getChatHistoryByDate(Map<String,Object> restrictionMap) throws DaoException{
		StringBuffer queryBuffer=new StringBuffer();
		Set<String> keySet = restrictionMap.keySet();
		List<String> keyList=new ArrayList<>(keySet);
		queryBuffer.append("SELECT * FROM tbl_chat_history WHERE ");
		for(int index = 0;index<keyList.size();index++){
			String key = keyList.get(index);
			if(key.indexOf("startDate") != -1){
				queryBuffer.append("to_date(user_query_time,'dd-mon-yy')").append(" >= ").append("to_date('").append(restrictionMap.get(key).toString()).append("','dd-mon-yy')");
			}else if(key.indexOf("endDate") != -1){
				queryBuffer.append("to_date(system_answer_time,'dd-mon-yy')").append(" <= ").append("to_date('").append(restrictionMap.get(key).toString()).append("','dd-mon-yy')");
			}else if(key.indexOf("user_id") != -1){
				queryBuffer.append("user_id").append(" = '").append(restrictionMap.get(key)).append("'");
			}
			if(index < (keyList.size()-1)){
				queryBuffer.append(" AND ");
			}else{
				queryBuffer.append(" ORDER BY user_query_time_stamp");
			}
		}
		return (List<Map<String,Object>>) abstractHibernateDAOAPI.processQuery(null, null, null, QueryOperationType.SELECT, QueryType.SQL, queryBuffer.toString());
	}
	
	private String formateChatHistoryDate(String inputDate) throws DaoException{
		String requiredDateStr = null;
		try {
			Date date = DateAndTimeUtil.getDateFromString("yyyy-mm-ddThh:mm:ss", inputDate);
			requiredDateStr=DateAndTimeUtil.getFormattedDateString(date, "dd-mmm-yy");
		} catch (ParseException e) {
			throw new DaoException(CommonFaultCode.PARSER_ERROR, e);
		}
		return requiredDateStr;
	}

}
