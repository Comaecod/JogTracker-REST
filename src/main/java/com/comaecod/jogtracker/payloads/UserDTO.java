package com.comaecod.jogtracker.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private String id;

	@NotEmpty(message = "Name must not be empty!")
	@Size(min = 4, message = "Name must have atleast four characters!")
	private String name;

	@NotEmpty(message = "Email must not be empty!")
	@Email(message = "Email address is not valid!")
	private String email;

	@NotEmpty(message = "Password must not be empty!")
//	@Size(min = 8, max = 20, message = "Password must be in the range of 8 to 20 characters!")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Password must contain atleast one uppercase letter, one lowercase letter, one digit, and a symbol and should be in the range of 8 to 20 characters.")
	private String password;

	@NotEmpty(message = "About must not be empty!")
	private String about;
	
	private Set<RoleDTO> roles = new HashSet<>();
}
