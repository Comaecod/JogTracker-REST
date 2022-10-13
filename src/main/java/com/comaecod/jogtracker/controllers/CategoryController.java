package com.comaecod.jogtracker.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comaecod.jogtracker.payloads.ApiResponse;
import com.comaecod.jogtracker.payloads.CategoryDTO;
import com.comaecod.jogtracker.services.CategoryService;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// POST -> Create A Category -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto) {
		CategoryDTO createdCategory = categoryService.createCategory(dto);
		return new ResponseEntity<CategoryDTO>(createdCategory, HttpStatus.CREATED);
	}

	// PUT -> Update A Category -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO dto,
			@PathVariable Integer categoryId) {
		CategoryDTO updatedCategory = categoryService.updateCategory(dto, categoryId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
	}

	// DELETE -> Delete A Category -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return new 
				ResponseEntity<ApiResponse>(
				new ApiResponse("Category with id: " + categoryId + " has been deleted successfully!", 
						true),
				HttpStatus.OK);
	}

	// GET -> Get A Category -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId) {
		CategoryDTO singleCategory = categoryService.getSingleCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(singleCategory, HttpStatus.OK);
	}

	// GET -> Get All Categories -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categories = categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDTO>>(categories, HttpStatus.OK);
	}
}
