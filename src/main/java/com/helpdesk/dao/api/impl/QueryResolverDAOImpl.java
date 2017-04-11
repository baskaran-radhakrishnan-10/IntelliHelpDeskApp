package com.helpdesk.dao.api.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.persistance_api.consenum.QueryOperationType;
import com.equiniti.persistance_api.consenum.QueryType;
import com.equiniti.persistance_api.hibernate.api.AbstractHibernateDAOAPI;
import com.helpdesk.dao.api.QueryResolverDAO;
import com.helpdesk.entity.ChatHistory;
import com.helpdesk.util.ApplicationConstants;

public class QueryResolverDAOImpl implements QueryResolverDAO{
	
	private AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI;

	public void setAbstractHibernateDAOAPI(AbstractHibernateDAOAPI<ChatHistory> abstractHibernateDAOAPI) {
		this.abstractHibernateDAOAPI = abstractHibernateDAOAPI;
	}
	
	/*@Override
	public List<ChatHistory> getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException {
		return abstractHibernateDAOAPI.getEntityList(ChatHistory.class, restrictionMap);
	}*/
	
	@Override
	public String getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException {
		
		String procedureQuery = ApplicationConstants.PROCEDURE_CALL_QUERY1;
		
		Map<String,Object> inParamMap = new LinkedHashMap<>();
		
		inParamMap.put("P_STR", restrictionMap.get("userQuery"));
		
		Map<String,Integer> outParamMap = new LinkedHashMap<>();
		
		outParamMap.put("M_SELECT_CLAUSE", java.sql.Types.VARCHAR);

		outParamMap.put("M_TABLE_NAME", java.sql.Types.VARCHAR);

		outParamMap.put("M_WHERE_CLAUSE", java.sql.Types.VARCHAR);
		
		Map<String,Object> procedureResultMap = abstractHibernateDAOAPI.callProcedure(procedureQuery, inParamMap, outParamMap);
		
		if(null != procedureResultMap && !procedureResultMap.isEmpty()){
			
			StringBuffer queryBuffer = new StringBuffer();
			
			queryBuffer.append("SELECT ").append(procedureResultMap.get("M_SELECT_CLAUSE")).append(" FROM ").append(procedureResultMap.get("M_TABLE_NAME")).append(" WHERE ").append(procedureResultMap.get("M_WHERE_CLAUSE"));
			
			System.out.println(queryBuffer);
			
			abstractHibernateDAOAPI.processQuery(null, null, null, QueryOperationType.SELECT, QueryType.SQL, queryBuffer.toString());
			
		}
		
		return "";
		
	}

}
