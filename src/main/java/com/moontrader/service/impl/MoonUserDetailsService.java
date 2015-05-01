package com.moontrader.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moontrader.dto.UserRole;
import com.moontrader.service.UserService;

@SuppressWarnings("deprecation")
@Service("customUserDetailsService")
public class MoonUserDetailsService implements UserDetailsService {

	protected static Logger logger = Logger.getLogger(MoonUserDetailsService.class);//log4j，不用解释了吧。。  
   
	@Autowired  
    private UserService userService;  

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {  
        UserDetails user = null;  
        try {  
            // 搜索数据库以匹配用户登录名.  
            // 我们可以通过dao使用Hibernate来访问数据库  
            System.out.println(username + "   用户页面输入的用户名"); 
            
            com.moontrader.dto.User localUser = userService.getUserByEmail(username);
            System.out.println(localUser.getEmail() + "   数据库取出的用户名");  
            // Populate the Spring User object with details from the dbUser  
            // Here we just pass the username, password, and access level  
            // getAuthorities() will translate the access level to the correct  
            // role type  
            // 用户名、密码、是否启用、是否被锁定、是否过期、权限  
            user = new User(localUser.getEmail(), localUser.getPassword(), true, true, true, true, getAuthorities(localUser));  
               
        } catch (Exception e) {  
            logger.error("用户信息错误！"+e.getMessage());  
            throw new UsernameNotFoundException("异常处理：检索用户信息未通过！");  
        }  
           
        return user;  
    }  
  
    /** 
     * 获得访问角色权限列表 
     *  
     * @param access 
     * @return 
     */  
    public Collection<GrantedAuthority> getAuthorities(com.moontrader.dto.User user) {  
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();  
        for(UserRole role:user.getRoles()){
        	authList.add(new GrantedAuthorityImpl(role.getName()));
        }
        System.out.println(authList.size()+"  权限列表长度");  
        return authList;  
    }  

}
