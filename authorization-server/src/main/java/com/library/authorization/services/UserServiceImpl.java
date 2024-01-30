package com.library.authorization.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userDetailsManager.loadUserByUsername(username);
    }

    @Override
    public void createUser(UserDetails user) {

        try {

            this.loadUserByUsername(user.getUsername());

        } catch (UsernameNotFoundException e) {

            this.userDetailsManager.createUser(User.builder()
                    .username(user.getUsername())
                    .password(this.passwordEncoder.encode(user.getPassword()))
                    .authorities(user.getAuthorities())
                    .build());
        }
    }

}
