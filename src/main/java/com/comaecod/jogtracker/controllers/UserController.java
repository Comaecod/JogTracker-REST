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
import com.comaecod.jogtracker.payloads.UserDTO;
import com.comaecod.jogtracker.services.UserService;

@RestController
@RequestMapping("/api/users/")
public class UserController {

	@Autowired
	private UserService userService;

	// POST -> Create User -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createdUser = userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(createdUser, HttpStatus.CREATED);
	}

	// PUT -> Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
			@PathVariable("userId") String userId) {
		UserDTO updatedUser = userService.updateUser(userDTO, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// DELETE -> Delete User -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User " + userId + " deleted successfully!", true),
				HttpStatus.OK);
	}

	// GET -> Get All Users -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	// GET -> Get Single User by ID
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUsers(@PathVariable String userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
}
