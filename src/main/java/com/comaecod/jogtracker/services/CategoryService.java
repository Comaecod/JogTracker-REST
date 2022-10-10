package com.comaecod.jogtracker.services;

import java.util.List;

import com.comaecod.jogtracker.payloads.CategoryDTO;

public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO dto);

	CategoryDTO updateCategory(CategoryDTO dto, Integer categoryId);

	void deleteCategory(Integer categoryId);

	CategoryDTO getSingleCategory(Integer categoryId);

	List<CategoryDTO> getAllCategories();
}
