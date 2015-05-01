package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moontrader.dao.UserDao;
import com.moontrader.dto.User;
import com.moontrader.dto.UserRole;
import com.moontrader.entities.UserEntity;
import com.moontrader.entities.UserRoleEntity;
import com.moontrader.service.ServiceException;
import com.moontrader.service.UserService;
import com.moontrader.util.EntityUtils;
import com.moontrader.util.Helper;

@Service("userService")
public class UserServiceImpl implements UserService {

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
	public void register(User user) throws ServiceException{
		Helper.checkNull(user, "user");
		UserEntity temp=userDao.findUserByEmail(user.getEmail());
		if(temp!=null) throw new ServiceException(user.getEmail()+"用户已存在");
		UserEntity userEntity=new UserEntity();
		userEntity.setEmail(user.getEmail());
		userEntity.setNickname(user.getNickname());
		userEntity.setPassword(Helper.digest(user.getPassword()));
		userEntity.setRoles(EntityUtils.dtoToEntity(user.getRoles(),userEntity));
		userDao.saveOrUpdate(userEntity);
		if(userEntity.getId()==1){
			UserRoleEntity manager=new UserRoleEntity();
			manager.setName("ROLE_MANAGER");
			manager.setUser(userEntity);
			UserRoleEntity roleUser=new UserRoleEntity();
			roleUser.setName("ROLE_USER");
			roleUser.setUser(userEntity);
			userEntity.getRoles().add(manager);
			userEntity.getRoles().add(roleUser);
		}
		user.setId(userEntity.getId());
	}
	@Transactional
	@Override
	public User getUser(int id) {
		UserEntity entity=userDao.findById(id);
		if(entity==null){
			return null;
		}
		User user=new User();
		user.setEmail(entity.getEmail());
		user.setId(entity.getId());
		user.setNickname(entity.getNickname());
		List<UserRole> roles=new ArrayList<UserRole>();
		for(UserRoleEntity et:entity.getRoles()){
			roles.add(EntityUtils.entityToDto(et));
		}
		user.setRoles(roles);
		return user;
	}
	@Transactional
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	@Override
	public User getUserByEmail(String email) {
		UserEntity userEntity=userDao.findUserByEmail(email);
		if(userEntity==null){
			return null;
		}
		User user=new User();
		user.setEmail(userEntity.getEmail());
		user.setId(userEntity.getId());
		user.setPassword(userEntity.getPassword());
		user.setNickname(userEntity.getEmail());
		List<UserRole> roles=new ArrayList<UserRole>();
		for(UserRoleEntity et:userEntity.getRoles()){
			roles.add(EntityUtils.entityToDto(et));
		}
		user.setRoles(roles);
		return user;
	}

}
