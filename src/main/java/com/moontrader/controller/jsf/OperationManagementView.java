package com.moontrader.controller.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.moontrader.dto.Operation;
import com.moontrader.service.OperationService;

@ManagedBean
@ViewScoped
public class OperationManagementView {
	@ManagedProperty("#{operationService}")
	private OperationService operationService;
	public OperationService getOperationService() {
		return operationService;
	}
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	private Operation operation=new Operation();
	private List<Operation> operations;
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	public void change(Operation operation){
		logger.debug("operation:"+operation);
		this.operation=operation;
	}
	@ManagedProperty("#{loginView}")
	private LoginView loginView;
	public LoginView getLoginView() {
		return loginView;
	}
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}
	@PostConstruct
	public void init(){
		operations=operationService.list(loginView.getUser());
	}
	public void insert(){
		operation=new Operation();
	}
	private static Logger logger=Logger.getLogger(OperationManagementView.class);
	public void onSubmit(){
		logger.debug("onSubmit debug:");
		if(operation.getId()==0){
			operationService.insert(operation,loginView.getUser());
		}
		else{
			operationService.update(operation,loginView.getUser());
		}
		this.init();
	}
}
