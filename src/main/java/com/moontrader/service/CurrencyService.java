package com.moontrader.service;

import java.util.List;

import com.moontrader.dto.Currency;
import com.moontrader.dto.User;

public interface CurrencyService {
	public void insert(Currency currency,User user);
	public void update(Currency currency,User user);
	public void delete(int id);
	public List<Currency> list(User user);
}
