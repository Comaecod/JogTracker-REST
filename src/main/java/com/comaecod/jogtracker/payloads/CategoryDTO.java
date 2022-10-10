package com.comaecod.jogtracker.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
	private Integer categoryId;

	@NotBlank(message = "Title cannot be blank!")
	@Size(min = 4, message = "Title should have atleast four characters!")
	private String categoryTitle;

	@NotBlank(message = "Description cannot be blank!")
	@Size(min = 10, message = "Description should have atleast ten characters!")
	private String categoryDescription;
}
