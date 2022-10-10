package com.comaecod.jogtracker.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.comaecod.jogtracker.entities.Category;
import com.comaecod.jogtracker.entities.Jog;
import com.comaecod.jogtracker.entities.User;
import com.comaecod.jogtracker.exceptions.ResourceNotFoundException;
import com.comaecod.jogtracker.payloads.AllJogPaginationResponse;
import com.comaecod.jogtracker.payloads.JogDTO;
import com.comaecod.jogtracker.repositories.CategoryRepo;
import com.comaecod.jogtracker.repositories.JogRepo;
import com.comaecod.jogtracker.repositories.UserRepo;
import com.comaecod.jogtracker.services.JogService;

@Service
public class JogServiceImpl implements JogService {

	@Autowired
	private JogRepo jogRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public JogDTO createJogData(JogDTO dto, String userId, Integer categoryId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
		Jog jog = modelMapper.map(dto, Jog.class);
		jog.setLocationImg("default.png");
		jog.setDatetime(new Date());
		jog.setUser(user);
		jog.setCategory(category);

		Jog newJog = jogRepo.save(jog);
		return modelMapper.map(newJog, JogDTO.class);

	}

	@Override
	public JogDTO updateJogData(JogDTO dto, Integer jogId) {
		Jog jogData = jogRepo.findById(jogId)
				.orElseThrow(() -> new ResourceNotFoundException("Jog Data", "id", Integer.toString(jogId)));
		jogData.setLocation(dto.getLocation());
		jogData.setLocationImg(dto.getLocationImg());
		Jog updatedJog = jogRepo.save(jogData);
		return modelMapper.map(updatedJog, JogDTO.class);
	}

	@Override
	public void deleteJogData(Integer jogId) {
		Jog jogData = jogRepo.findById(jogId)
				.orElseThrow(() -> new ResourceNotFoundException("Jog Data", "id", Integer.toString(jogId)));
		jogRepo.delete(jogData);
	}

	@Override
	public JogDTO getOneJogDataById(Integer jogId) {
		Jog jogData = jogRepo.findById(jogId)
				.orElseThrow(() -> new ResourceNotFoundException("Jog Data", "id", Integer.toString(jogId)));
		return modelMapper.map(jogData, JogDTO.class);
	}

	// Pagination - @Comaecod (with metadata about pages)
	@Override
	public AllJogPaginationResponse getAllJogData(Integer pageNumber, Integer pageSize) {
//		int pageSize = 5;
//		int pageNumber = 1;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Jog> jogDataInPage = jogRepo.findAll(pageable);
		List<Jog> allJogData = jogDataInPage.getContent();

		List<JogDTO> alljogDTOs = allJogData.stream().map(jogData -> modelMapper.map(jogData, JogDTO.class))
				.collect(Collectors.toList());

		AllJogPaginationResponse response = new AllJogPaginationResponse();

		response.setContent(alljogDTOs);
		response.setPageNumber(jogDataInPage.getNumber());
		response.setPageSize(jogDataInPage.getSize());
		response.setTotalRecords(jogDataInPage.getTotalElements());
		response.setTotalPages(jogDataInPage.getTotalPages());
		response.setLastPage(jogDataInPage.isLast());

		return response;
	}

	@Override
	public List<JogDTO> getJogDataByUser(String userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<Jog> jogs = jogRepo.findByUser(user);
		List<JogDTO> jogDTOs = jogs.stream().map(jog -> modelMapper.map(jog, JogDTO.class))
				.collect(Collectors.toList());
		return jogDTOs;
	}

	@Override
	public List<JogDTO> getJogDataByCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", Integer.toString(categoryId)));
		List<Jog> jogs = jogRepo.findByCategory(category);
		List<JogDTO> jogDTOs = jogs.stream().map(jog -> modelMapper.map(jog, JogDTO.class))
				.collect(Collectors.toList());
		return jogDTOs;
	}

	@Override
	public List<JogDTO> getAllJogDataBySearch(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
