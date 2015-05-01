package com.moontrader.service;

import java.util.List;

import com.moontrader.dto.User;

public interface RegisterUserService {
	public List<User> list();
	public void agree(User user);
	public void delete(User user);
}
