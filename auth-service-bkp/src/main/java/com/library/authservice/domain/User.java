package com.library.authservice.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.library.authservice.enums.Authorities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

@Document
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Indexed(unique = true)
	private String username;

	private String password;

	private boolean activated;

	private String activationKey;

	private String resetPasswordKey;

	private Set<Authorities> authorities = new HashSet<>();

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActivated() {
		return activated;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public String getResetPasswordKey() {
		return resetPasswordKey;
	}

	public List<Authorities> getAuthorities() {
		return new ArrayList<Authorities>(authorities);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}

	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return activated;
	}

}
