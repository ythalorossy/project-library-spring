package com.library.accountservice.service;

import com.library.accountservice.dto.UserDto;
import com.library.accountservice.dto.UserRegistrationDto;

public interface AccountService {
	/**
	 * Invokes Auth Service user creation
	 *
	 * @param user
	 * @return created account
	 */
	UserDto create(UserRegistrationDto user);
}
