package com.github.makijaveli.springbootjwtauth.service;

import com.github.makijaveli.springbootjwtauth.filter.JWTUserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MockDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return JWTUserDetailsImpl.build(1L, "userName", Arrays.asList("user", "admin"));
    }

}
