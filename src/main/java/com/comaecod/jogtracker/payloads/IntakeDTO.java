package com.comaecod.jogtracker.payloads;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntakeDTO {
	private int id;

	@Min(value = 10, message = "Energy should be atleast 10 calories!")
	@Max(value = 1000, message = "Energy should be atmost 1000 calories!")
	private int energy;

	@Min(value = 10, message = "Quantity should be atleast 10 grams!")
	@Max(value = 1000, message = "Quantity should be atmost 1000 grams!")
	private int quantity;

	@NotEmpty(message = "Type of food must not be empty!")
	private String food;
	private Date date;
}
