package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.UserDao;
import com.moontrader.dto.User;
import com.moontrader.entities.UserEntity;
import com.moontrader.entities.UserRoleEntity;
import com.moontrader.service.RegisterUserService;
import com.moontrader.util.EntityUtils;

@Service("registerUserService")
public class RegisterUserServiceImpl implements RegisterUserService {
	
	@Autowired
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Transactional
	@Override
	public List<User> list() {
		List<UserEntity> entities=userDao.listRegisterEntities("ROLE_REGISTERED");
		List<User> users=new ArrayList<User>();
		for(UserEntity entity:entities){
			User user=EntityUtils.entityToDto(entity);
			users.add(user);
		}
		return users;
	}
	@Transactional
	@Override
	public void agree(User user) {
		UserRoleEntity userRole=new UserRoleEntity();
		userRole.setName("ROLE_USER");
		UserEntity entity=new UserEntity();
		List<UserRoleEntity> roles=new ArrayList<UserRoleEntity>();
		roles.add(userRole);
		entity.setEmail(user.getEmail());
		entity.setId(user.getId());
		entity.setNickname(user.getNickname());
		entity.setPassword(user.getPassword());
		entity.setRoles(roles);
		userDao.saveOrUpdate(entity);
	}
	@Transactional
	@Override
	public void delete(User user) {
		userDao.delete(userDao.findById(user.getId()));
	}
}
