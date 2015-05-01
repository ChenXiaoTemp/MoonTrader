package com.moontrader.service;

import java.util.List;

import com.moontrader.dto.User;

public interface UserService {
	public void register(User user) throws ServiceException;
	public User getUser(int id);
	public User getUserByEmail(String email);
	public List<User> list();
}
