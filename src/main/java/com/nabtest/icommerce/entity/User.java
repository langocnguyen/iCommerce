package com.nabtest.icommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "nab_user")
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	@NotNull(message = "Username may not be null!")
	private String username;
	
	private String password;

//	private JSONObject roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public JSONObject getRoles() {
//		return roles;
//	}
//
//	public void setRoles(JSONObject roles) {
//		this.roles = roles;
//	}
	
}
