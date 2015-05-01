package com.moontrader.controller.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.moontrader.dto.Currency;
import com.moontrader.service.CurrencyService;

@ManagedBean
@ViewScoped
public class CurrencyManagementView implements Serializable{
	private static final long serialVersionUID = -4629222052939325134L;

	private List<Currency> currencies;
	
	private Currency currency;
	
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}
	
	@ManagedProperty("#{currencyService}")
	private CurrencyService currencyService;
	
	@ManagedProperty("#{loginView}")
	private LoginView loginView;
	
	public LoginView getLoginView() {
		return loginView;
	}

	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}

	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@PostConstruct
	public void init(){
		currencies=currencyService.list(loginView.getUser());
	}
	
	public void changeCurrency(Currency currency){
		this.currency=currency;
	}
	
	public void insertCurrency(){
		this.currency=new Currency();
	}
	
	public void onSubmit(){
	    if(currency.getId()==0){
	    	//insert
	    	currencyService.insert(currency,loginView.getUser());
	    }	
	    else{
	    	currencyService.update(currency,loginView.getUser());
	    }
	    this.init();
	}
}
