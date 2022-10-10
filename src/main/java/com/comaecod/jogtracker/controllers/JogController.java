package com.comaecod.jogtracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.comaecod.jogtracker.payloads.AllJogPaginationResponse;
import com.comaecod.jogtracker.payloads.ApiResponse;
import com.comaecod.jogtracker.payloads.JogDTO;
import com.comaecod.jogtracker.services.JogService;

@RestController
@RequestMapping("/api/")
public class JogController {

	@Autowired
	JogService jogService;

	@PostMapping("/user/{userId}/category/{categoryId}/jogdata")
	public ResponseEntity<JogDTO> createJogData(@RequestBody JogDTO dto, @PathVariable String userId,
			@PathVariable Integer categoryId) {
		JogDTO createdJogData = jogService.createJogData(dto, userId, categoryId);
		return new ResponseEntity<JogDTO>(createdJogData, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/jogdata")
	public ResponseEntity<List<JogDTO>> getJogsByUser(@PathVariable String userId) {
		List<JogDTO> jogDataByUser = jogService.getJogDataByUser(userId);
		return new ResponseEntity<List<JogDTO>>(jogDataByUser, HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/jogdata")
	public ResponseEntity<List<JogDTO>> getJogsByCategory(@PathVariable Integer categoryId) {
		List<JogDTO> jogDataByCategory = jogService.getJogDataByCategory(categoryId);
		return new ResponseEntity<List<JogDTO>>(jogDataByCategory, HttpStatus.OK);
	}

	// Pagination - @Comaecod (with metadata about pages)
	@GetMapping("/jogdata")
	public ResponseEntity<AllJogPaginationResponse> getAllJogData(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
		AllJogPaginationResponse allJogData = jogService.getAllJogData(pageNumber, pageSize);
		return new ResponseEntity<AllJogPaginationResponse>(allJogData, HttpStatus.OK);
	}

	@GetMapping("/jogdata/{jogId}")
	public ResponseEntity<JogDTO> getJogDataById(@PathVariable Integer jogId) {
		JogDTO jogData = jogService.getOneJogDataById(jogId);
		return new ResponseEntity<JogDTO>(jogData, HttpStatus.OK);
	}

	@DeleteMapping("/jogdata/{jogId}")
	public ApiResponse deleteJogData(@PathVariable Integer jogId) {
		jogService.deleteJogData(jogId);
		return new ApiResponse("Jog Data with ID: " + jogId + " has been deleted successfully!", true);
	}

	@PutMapping("/jogdata/{jogId}")
	public ResponseEntity<JogDTO> updateJogData(@RequestBody JogDTO dto, @PathVariable Integer jogId) {
		JogDTO updateJogData = jogService.updateJogData(dto, jogId);
		return new ResponseEntity<JogDTO>(updateJogData, HttpStatus.OK);
	}

}
