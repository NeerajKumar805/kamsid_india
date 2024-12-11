package com.india.kamsid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.india.kamsid.entities.Admin;
import com.india.kamsid.entities.FormData;
import com.india.kamsid.services.ProductService;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Admin loginRequest) {
		try {
			// Create a UsernamePasswordAuthenticationToken from the request
			Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
					loginRequest.getPassword());

			// Perform authentication
			authenticationManager.authenticate(authentication);

			// If successful, return success response
			return ResponseEntity.ok("Login successful for user: " + loginRequest.getUsername());

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("Invalid username or password");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(500).body("Authentication failed: " + e.getMessage());
		}
	}
}
