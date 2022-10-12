package com.comaecod.jogtracker.config;

public class AppConstantsDefaults {
	
	public static final String PAGE_NUMBER = "0";
	public static final String PAGE_SIZE = "5";
	public static final String SORT_FIELD = "jogId";
	public static final String SORT_DIRECTION = "asc";
	
	public static final String JWT_SECRET = "jwtTokenKey";
	public static final int JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	public static final int ROLE_ADMIN = 1001;
	public static final int ROLE_USER = 1005;
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
}
