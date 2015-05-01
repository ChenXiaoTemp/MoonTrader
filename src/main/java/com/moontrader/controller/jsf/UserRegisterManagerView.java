package com.moontrader.controller.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.moontrader.dto.User;
import com.moontrader.service.RegisterUserService;
import com.moontrader.service.UserService;

@ManagedBean
@ViewScoped
public class UserRegisterManagerView {
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	private List<User> users;

	@ManagedProperty("#{registerUserService}")
	private RegisterUserService registerUserService;
	public RegisterUserService getRegisterUserService() {
		return registerUserService;
	}
	public void setRegisterUserService(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}
	@PostConstruct
	public void init(){
		users=registerUserService.list();
	}
	public void agree(User user){
		registerUserService.agree(user);
	}
	public void unagree(User user){
		registerUserService.delete(user);
	}
}
