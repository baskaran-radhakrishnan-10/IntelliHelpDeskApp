/**
 * 
 */
package com.helpdesk.dao.api;

import java.util.List;
import java.util.Map;

import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.entity.RolesResources;


/**
 * @author 
 *
 */
public interface RoleResourceDAO {

	public void addRolesResources(RolesResources rolesResources) throws DaoException;

    public void removeRolesResources(Map<String, Object> restrictionMap) throws DaoException;

    public void updateRolesResources(RolesResources rolesResources) throws DaoException;

    public List<RolesResources> getRolesResources(Map<String, Object> restrictionMap) throws DaoException;
    
    public List<RolesResources> listRolesResources(Map<String, Object> restrictionMap) throws DaoException;
}
