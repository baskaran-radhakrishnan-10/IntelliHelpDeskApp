package com.helpdesk.service.api.impl;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equiniti.exception.api.exception.APIException;
import com.equiniti.exception.api.exception.DaoException;
import com.equiniti.exception.api.exception.EventException;
import com.equiniti.exception.api.exception.SecurityException;
import com.equiniti.security.crypto.EncryptionDecryption;
import com.helpdesk.dao.api.LoginDAO;
import com.helpdesk.entity.Roles;
import com.helpdesk.entity.User;
import com.helpdesk.form.model.LoginModelAttribute;
import com.helpdesk.service.api.LoginService;
import com.helpdesk.service.api.RoleResourceService;
import com.helpdesk.util.ApplicationConstants;

@Service("loginService")
public class LoginServiceImpl implements LoginService{

	private static final Logger LOG=Logger.getLogger(LoginServiceImpl.class); 

	@Autowired
	private HttpSession session;

	@Autowired
	private RoleResourceService roleResourceService;

	private EncryptionDecryption cryptoService;

	private LoginDAO loginDAO;

	public void setCryptoService(EncryptionDecryption cryptoService) {
		this.cryptoService = cryptoService;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	//private CacheInstance CACHE_INS;

	@Override
	public LoginModelAttribute doLogin(LoginModelAttribute modelAttribute) throws APIException{

		LOG.debug("START doLogin Method");

		try {

			//CACHE_INS = CacheInstance.getInstance();

			User user=loginDAO.getUserByUserIdAndPassword(modelAttribute.getUserId().trim().toLowerCase(), cryptoService.encrypt(modelAttribute.getPassword()));

			if(null == user){
				modelAttribute.getModel().addAttribute("error", "Invalid user-id or password.");
				modelAttribute.setSuccess(false);
				modelAttribute.setResultMapping(ApplicationConstants.LOGIN_PAGE);
				return modelAttribute;
			}

			Roles role=user.getRoleId();

			if(null == role){
				modelAttribute.getModel().addAttribute("error", "Invalid Role Permission Assigned.");
				modelAttribute.setSuccess(false);
				modelAttribute.setResultMapping(ApplicationConstants.LOGIN_PAGE);
				return modelAttribute;
			}

			/*if(user.isFirstLogin()){
				modelAttribute.setSuccess(true);
				modelAttribute.setResultMapping(ApplicationConstants.RESET_PASSWORD_PAGE);
				return modelAttribute;
			}*/

			//List<RolesResources> roleResourceList=roleResourceService.getRolesResourcesByRoleId(role.getGkey());

			session.setAttribute(ApplicationConstants.USER_OBJ, user);

			session.setAttribute(ApplicationConstants.USER_ID, user.getUserId());

			session.setAttribute(ApplicationConstants.USER_NAME, user.getUserFullName());

			session.setAttribute(ApplicationConstants.IS_LOGGED_IN, true);

			session.setAttribute(ApplicationConstants.ROLE_ID, role.getRoleName());

			session.setAttribute(ApplicationConstants.FIRST_LOGIN, user.isFirstLogin());

			//CACHE_INS.removeAllItemFromGroup(user.getUserId());

			//CACHE_INS.putItemInCache(ApplicationConstants.ROLE_RESOURCE_INFO, roleResourceList, user.getUserId());

			LOG.debug("END doLogin Method");

		}catch (EventException e) {
			throw new APIException(e.getFaultCode(), e);
		} /*catch (CacheException e) {
			throw new APIException(CommonFaultCode.UNKNOWN_ERROR, e);
		}*/ catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAttribute;
	}

}
