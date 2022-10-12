package com.comaecod.jogtracker.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.comaecod.jogtracker.config.AppConstantsDefaults;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenHelper {

	// Extract username from token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract expiration date from JWT token
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// For extracting any info from the token we would need the JWT_SECRET key
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(AppConstantsDefaults.JWT_SECRET).parseClaimsJws(token).getBody();
	}

	// Check if the token has expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Generate token from user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	/*
	 * While creating the token - 1. Define claims of the token, like issuer,
	 * expiration, subject and the ID. 2. Sign the JWT using the HS512 algorithm and
	 * secret key 3. According to the JWT compact serialization, compaction of the
	 * JWT to a URL-safe string
	 */
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * AppConstantsDefaults.JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, AppConstantsDefaults.JWT_SECRET).compact();
	}

	// Validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
