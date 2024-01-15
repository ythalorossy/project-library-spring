package com.library.authservice.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import com.library.authservice.domain.User;
import com.library.authservice.enums.Authorities;
import com.library.authservice.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Override
	public User create(User user) {
		throwIfUsernameExists(user.getUsername());

		String hash = passwordEncoder.encode(user.getPassword());
		user.setPassword(hash);
		user.setActivated(Boolean.TRUE);
		user.setAuthorities(new HashSet<>(Collections.singletonList(Authorities.ROLE_USER)));

		return userRepository.save(user);
	}

	private void throwIfUsernameExists(String username) {
		Optional<User> existingUser = userRepository.findByUsername(username);
		existingUser.ifPresent((user) -> {
			throw new IllegalArgumentException("User not available");
		});
	}

}