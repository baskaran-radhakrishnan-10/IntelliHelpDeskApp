package com.helpdesk.dao.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.exception.api.faultcode.CommonFaultCode;
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSolutionForQuery(Map<String, Object> restrictionMap) throws DaoException {

		Map<String,Object> returnMap = new HashMap<>();

		String procedureQuery = ApplicationConstants.PROCEDURE_CALL_QUERY1;

		Map<String,Object> inParamMap = new LinkedHashMap<>();

		inParamMap.put("P_STR", restrictionMap.get("userQuery"));

		Map<String,Integer> outParamMap = new LinkedHashMap<>();

		outParamMap.put("M_SELECT_CLAUSE", java.sql.Types.VARCHAR);

		outParamMap.put("M_TABLE_NAME", java.sql.Types.VARCHAR);

		outParamMap.put("M_WHERE_CLAUSE", java.sql.Types.VARCHAR);

		Map<String,Object> procedureResultMap = abstractHibernateDAOAPI.callProcedure(procedureQuery, inParamMap, outParamMap);

		if(null != procedureResultMap && !procedureResultMap.isEmpty()){
			
			if(null == procedureResultMap.get("M_SELECT_CLAUSE") || null == procedureResultMap.get("M_TABLE_NAME")){
				
				returnMap.put("SYS_RESPONSE", "Unable to Find Solution, Please Tune your Request.");
				
				return returnMap;
				
			}

			StringBuffer queryBuffer = new StringBuffer();

			queryBuffer.append("SELECT ").append(procedureResultMap.get("M_SELECT_CLAUSE")).append(" FROM ").append(procedureResultMap.get("M_TABLE_NAME"));
			
			if(null != procedureResultMap.get("M_TABLE_NAME")){
				
				queryBuffer.append(" WHERE ").append(procedureResultMap.get("M_WHERE_CLAUSE"));
				
			}

			List<Map<String,Object>> resultList=(List<Map<String, Object>>) abstractHibernateDAOAPI.processQuery(null, null, null, QueryOperationType.SELECT, QueryType.SQL, queryBuffer.toString());

			if(null != resultList && !resultList.isEmpty()){

				for(Map<String,Object> objMap : resultList){

					Set<String> keySet =  objMap.keySet();

					if(keySet.size() == 1 && resultList.size() == 1){

						List<String> keyList = new ArrayList<>(keySet);

						objMap.get(keyList.get(0));
						
						returnMap.put("SYS_RESPONSE", objMap.get(keyList.get(0)));
						
						returnMap.put("IS_JSON", "F");

					}else{

						ObjectMapper mapper = new ObjectMapper();

						try {
							
							mapper.writeValueAsString(resultList);
							
							returnMap.put("SYS_RESPONSE", mapper.writeValueAsString(resultList));
							
							returnMap.put("IS_JSON", "T");
							
						} catch (JsonGenerationException e) {
							throw new DaoException(CommonFaultCode.OBJECT_MAPPER_FAILED_ERROR,e);
						} catch (JsonMappingException e) {
							throw new DaoException(CommonFaultCode.OBJECT_MAPPER_FAILED_ERROR,e);
						} catch (IOException e) {
							throw new DaoException(CommonFaultCode.OBJECT_MAPPER_FAILED_ERROR,e);
						}

					}

				}

			}

		}

		return returnMap;

	}

}
