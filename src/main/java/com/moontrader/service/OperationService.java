package com.moontrader.service;

import java.util.List;

import com.moontrader.dto.Operation;
import com.moontrader.dto.User;

public interface OperationService {
	public void insert(Operation operation,User user);
	public void update(Operation operation,User user);
	public void delete(int id);
	public Operation get(int id);
	public List<Operation> list(User user);
}
