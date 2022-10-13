package com.comaecod.jogtracker.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JogDTO {

	private Integer jogId;
	
	private String locationImg;
	
	private Date datetime;

	@NotBlank(message = "Location cannot be blank!")
	private String location;

	private UserDTO user;
	
	private CategoryDTO category;
	
	private Set<IntakeDTO> intake = new HashSet<>();
}
