package com.comaecod.jogtracker.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.comaecod.jogtracker.config.AppConstantsDefaults;
import com.comaecod.jogtracker.entities.Role;
import com.comaecod.jogtracker.entities.User;
import com.comaecod.jogtracker.exceptions.ResourceNotFoundException;
import com.comaecod.jogtracker.payloads.UserDTO;
import com.comaecod.jogtracker.repositories.RoleRepo;
import com.comaecod.jogtracker.repositories.UserRepo;
import com.comaecod.jogtracker.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	/*
	 * Deprecated -> User ModelMapper instead
	 * 
	 * public User DTOToUser(UserDTO dto) { User user = new User();
	 * user.setId(dto.getId()); user.setName(dto.getName());
	 * user.setEmail(dto.getEmail()); user.setPassword(dto.getPassword());
	 * user.setAbout(dto.getAbout()); return user; }
	 * 
	 * public UserDTO UserToDTO(User user) { UserDTO dto = new UserDTO();
	 * dto.setId(user.getId()); dto.setName(user.getName());
	 * dto.setEmail(user.getEmail()); dto.setPassword(user.getPassword());
	 * dto.setAbout(user.getAbout()); return dto; }
	 */

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public User DTOToUser(UserDTO dto) {
		User user = modelMapper.map(dto, User.class);
		return user;
	}

	public UserDTO UserToDTO(User user) {
		UserDTO dto = modelMapper.map(user, UserDTO.class);
		return dto;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = DTOToUser(userDTO);
		User savedUser = userRepo.save(user);
		return UserToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, String userId) {
		User user = userRepo
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		// user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());

		User updatedUser = userRepo.save(user);
		
		return UserToDTO(updatedUser);

	}

	@Override
	public UserDTO getUserById(String userId) {
		User user = userRepo
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		return UserToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepo.findAll();
		
		List<UserDTO> userDTOs = users
				.stream()
				.map(user -> UserToDTO(user))
				.collect(Collectors.toList());
		
		return userDTOs;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepo
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		
		userRepo.delete(user);
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role role = roleRepo.findById(AppConstantsDefaults.ROLE_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = userRepo.save(user);
		
		return modelMapper.map(newUser, UserDTO.class);
	}

}
