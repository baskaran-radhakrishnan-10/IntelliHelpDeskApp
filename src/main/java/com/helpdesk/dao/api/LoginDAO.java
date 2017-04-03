package com.helpdesk.dao.api;

import com.equiniti.exception.api.exception.DaoException;
import com.helpdesk.entity.User;

public interface LoginDAO {
	
	public User getUserByUserIdAndPassword(String userId,String password) throws DaoException;

}
