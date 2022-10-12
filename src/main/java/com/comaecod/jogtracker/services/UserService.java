package com.comaecod.jogtracker.services;

import java.util.List;

import com.comaecod.jogtracker.payloads.UserDTO;

public interface UserService {
	UserDTO registerUser(UserDTO userDTO);

	UserDTO createUser(UserDTO userDTO);

	UserDTO updateUser(UserDTO userDTO, String id);

	UserDTO getUserById(String userId);

	List<UserDTO> getAllUsers();

	void deleteUser(String userId);

}
