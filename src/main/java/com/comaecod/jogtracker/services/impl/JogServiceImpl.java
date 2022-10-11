package com.comaecod.jogtracker.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	// Get all jog data - Paginated(with metadata of pages) - @Comaecod + Sorting
	@Override
	public AllJogPaginationResponse getAllJogData(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDirection) {
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)); //default -> ascending
//		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()); // descending

//		Sort sort = null;
//		if (sortDirection.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else if (sortDirection.equalsIgnoreCase("desc")) {
//			sort = Sort.by(sortBy).descending();
//		}

		// Or using ternary oprator
		Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
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

	// Get all jog data by user - Paginated(with metadata of pages) - @Comaecod
	@Override
	public AllJogPaginationResponse getJogDataByUser(String userId, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Page<Jog> jogsInPage = jogRepo.findByUser(user, pageable);
		List<Jog> allJogDataByUser = jogsInPage.getContent();
		List<JogDTO> jogDTOsByUser = allJogDataByUser.stream().map(jog -> modelMapper.map(jog, JogDTO.class))
				.collect(Collectors.toList());
		AllJogPaginationResponse response = new AllJogPaginationResponse();

		response.setContent(jogDTOsByUser);
		response.setPageNumber(jogsInPage.getNumber());
		response.setPageSize(jogsInPage.getSize());
		response.setTotalRecords(jogsInPage.getTotalElements());
		response.setTotalPages(jogsInPage.getTotalPages());
		response.setLastPage(jogsInPage.isLast());

		return response;
	}

	/*
	 * Original getJogDataByUser()
	 * 
	 * @Override public List<JogDTO> getJogDataByUser(String userId) { User user =
	 * userRepo.findById(userId).orElseThrow(() -> new
	 * ResourceNotFoundException("User", "id", userId)); List<Jog> jogs =
	 * jogRepo.findByUser(user); List<JogDTO> jogDTOs = jogs.stream().map(jog ->
	 * modelMapper.map(jog, JogDTO.class)) .collect(Collectors.toList()); return
	 * jogDTOs; }
	 */

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
//		List<Jog> jogData = jogRepo.findByLocationContaining("%" + keyword + "%");
		List<Jog> jogData = jogRepo.findByLocationContaining(keyword);
		List<JogDTO> jogDataDTOs = jogData.stream().map(jog -> modelMapper.map(jog, JogDTO.class))
				.collect(Collectors.toList());
		return jogDataDTOs;
	}

}
