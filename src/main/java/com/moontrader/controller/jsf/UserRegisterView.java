package com.moontrader.controller.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.moontrader.dto.User;
import com.moontrader.dto.UserRole;
import com.moontrader.service.ServiceException;
import com.moontrader.service.UserService;

@ManagedBean
@ViewScoped
public class UserRegisterView {
	
	@ManagedProperty("#{userService}")
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user=new User();
	
	public String register(){
		UserRole userRole=new UserRole();
		userRole.setName("ROLE_REGISTERED");
		user.getRoles().add(userRole);
		try {
			userService.register(user);
		} catch (ServiceException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", e.getMessage()));
		}
		return "/faces/users/login.xhtml";
	}
}
