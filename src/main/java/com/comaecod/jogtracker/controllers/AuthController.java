package com.comaecod.jogtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comaecod.jogtracker.exceptions.APIException;
import com.comaecod.jogtracker.payloads.JWTAuthenticationRequest;
import com.comaecod.jogtracker.payloads.JWTAuthenticationResponse;
import com.comaecod.jogtracker.payloads.UserDTO;
import com.comaecod.jogtracker.security.JWTTokenHelper;
import com.comaecod.jogtracker.services.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	// JWT
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	// POST -> REGISTER API (for all users)
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
		UserDTO registeredUser = userService.registerUser(userDTO);
		return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
	}
	
	// POST -> LOGIN API (for all users)
	@PostMapping("/login")
	public ResponseEntity<JWTAuthenticationResponse> 
		createTokenAndLogin(@RequestBody JWTAuthenticationRequest request)
			throws Exception {
		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JWTAuthenticationResponse response = new JWTAuthenticationResponse();
		response.setToken(token);
		return new ResponseEntity<JWTAuthenticationResponse>(response, HttpStatus.OK);
	}
	
	// Authenticate User -> For LOGIN API
	// Verify if the user even is registered and exists and give the token for maintaining session
	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			// System.out.println("Invalid username or password!");
			throw new APIException("Invalid username or password!");
		}

	}
}
