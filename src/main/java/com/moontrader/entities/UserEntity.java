package com.moontrader.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserEntity {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private int id;
	private String nickname;
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	private String email;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	private List<UserRoleEntity> roles;
	public List<UserRoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(List<UserRoleEntity> roles) {
		this.roles = roles;
	}
}
