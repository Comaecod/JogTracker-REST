package com.comaecod.jogtracker.payloads;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
	
	private String username;
	
	private String password;
}
