package com.nabtest.icommerce.controller;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nabtest.icommerce.dto.CustomUserDetails;
import com.nabtest.icommerce.dto.request.LoginRequest;
import com.nabtest.icommerce.dto.response.LoginResponse;
import com.nabtest.icommerce.token.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1")
public class LoginRestController {

	private AuthenticationManager authenticationManager;

	private JwtTokenProvider tokenProvider;
	
	public LoginRestController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping(path = "/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}
}

