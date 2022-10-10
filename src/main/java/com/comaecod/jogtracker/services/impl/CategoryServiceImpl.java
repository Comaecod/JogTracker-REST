package com.comaecod.jogtracker.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comaecod.jogtracker.entities.Category;
import com.comaecod.jogtracker.exceptions.ResourceNotFoundException;
import com.comaecod.jogtracker.payloads.CategoryDTO;
import com.comaecod.jogtracker.repositories.CategoryRepo;
import com.comaecod.jogtracker.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO dto) {
		Category category = modelMapper.map(dto, Category.class);
		Category addedCategory = categoryRepo.save(category);
		return modelMapper.map(addedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO dto, Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
		category.setCategoryTitle(dto.getCategoryTitle());
		category.setCategoryDescription(dto.getCategoryDescription());
		Category updatedCategory = categoryRepo.save(category);
		return modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
		categoryRepo.delete(category);
	}

	@Override
	public CategoryDTO getSingleCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
		return modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDTO> categoriesDTO = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoriesDTO;
	}

}
