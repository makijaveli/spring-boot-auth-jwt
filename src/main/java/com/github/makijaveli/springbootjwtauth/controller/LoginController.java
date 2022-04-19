package com.github.makijaveli.springbootjwtauth.controller;

import com.github.makijaveli.springbootjwtauth.config.security.JwtTokenUtil;
import com.github.makijaveli.springbootjwtauth.filter.JWTUserDetailsImpl;
import com.github.makijaveli.springbootjwtauth.pojo.request.LoginRequest;
import com.github.makijaveli.springbootjwtauth.pojo.response.LoginResponse;
import com.github.makijaveli.springbootjwtauth.service.MockDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class LoginController {

    private final JwtTokenUtil jwtTokenUtil;
    private final MockDetailsService mockDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request) {
        JWTUserDetailsImpl userDetails = (JWTUserDetailsImpl) mockDetailsService.loadUserByUsername(request.getUserName());
        return getLoginResponseFromUserDetails(userDetails);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody final String token) {
        JWTUserDetailsImpl userDetails = (JWTUserDetailsImpl) jwtTokenUtil.getUserDetails(token);
        return getLoginResponseFromUserDetails(userDetails);
    }

    private ResponseEntity<LoginResponse> getLoginResponseFromUserDetails(final JWTUserDetailsImpl userDetails) {
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtTokenUtil.generateAccessToken(userDetails.getId(), userDetails.getUsername(), roles);

        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

}
