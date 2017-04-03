package com.helpdesk.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.dao.api.RoleResourceDAO;
import com.helpdesk.entity.RolesResources;
import com.helpdesk.service.api.RoleResourceService;


@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService{
	
	@Autowired
	private RoleResourceDAO roleResourceDAO;
	
	@Override
	public void addRolesResources(RolesResources rolesResources) {
		
	}

	@Override
	public void removeRolesResources(int gKey) {
		
	}

	@Override
	public void updateRolesResources(RolesResources rolesResources) {
		
	}

	@Override
	public List<RolesResources> getRolesResourcesByRoleId(int roleId) {
		List<RolesResources> rolesResourcesList=null;
		Map<String, Object> restrictionMap=new HashMap<>();
		try {
			restrictionMap.put("role_id", roleId);
			rolesResourcesList=roleResourceDAO.getRolesResources(restrictionMap);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return rolesResourcesList;
	}

	@Override
	public List<RolesResources> listRolesResources() {
		return null;
	}

}
