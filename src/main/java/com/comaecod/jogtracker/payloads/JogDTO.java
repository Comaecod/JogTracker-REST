package com.comaecod.jogtracker.payloads;

import java.util.Date;

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
	private String location;
	private UserDTO user;
	private CategoryDTO category;
}
