package com.moontrader.controller.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.moontrader.dto.User;
import com.moontrader.dto.UserRole;
import com.moontrader.service.UserService;
import com.moontrader.util.Assert;

@ManagedBean
@SessionScoped
public class LoginView implements Serializable {
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private boolean isManager=false;;

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	private static final long serialVersionUID = 6712257936947705834L;
	private String email;
	private String password;
	private User user = null;
	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager = null;

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

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
	private Logger logger=Logger.getLogger(LoginView.class);
	public String login() {
		try {
			logger.debug("Username:"+email+" password:"+password);
			Authentication request = new UsernamePasswordAuthenticationToken(
					this.getEmail(), this.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			user=userService.getUserByEmail(email);
			Assert.assertNotNull(user, "User");
			this.isManager=false;
			for(UserRole role:user.getRoles()){
				if(role.getName().trim().toUpperCase().equals("ROLE_MANAGER")){
					this.isManager=true;
					break;
				}
			}
		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", e.getMessage()));
			return "#";
		}
		return "/faces/trade/diary.xhtml";
	}
	@PostConstruct
	public void init() {
	}
	public String logout(){
			//SecurityContextHolder.getContext().setAuthentication(null);
			try {
				FacesContext.getCurrentInstance()
				   .getExternalContext().redirect("/MoonTrader/j_spring_security_logout");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "/faces/trade/diary.xhtml";
	}
}
