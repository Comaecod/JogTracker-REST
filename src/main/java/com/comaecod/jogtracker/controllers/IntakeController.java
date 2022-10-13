package com.comaecod.jogtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comaecod.jogtracker.payloads.ApiResponse;
import com.comaecod.jogtracker.payloads.IntakeDTO;
import com.comaecod.jogtracker.services.IntakeService;

@RestController
@RequestMapping("/api/")
public class IntakeController {

	@Autowired
	private IntakeService intakeService;

	// POST -> Create Intake
	@PostMapping("/jogdata/{jogId}/intake")
	public ResponseEntity<IntakeDTO> 
		createIntake(@RequestBody IntakeDTO dto, @PathVariable Integer jogId) {
		IntakeDTO createdIntake = intakeService.createIntake(dto, jogId);
		return new ResponseEntity<IntakeDTO>(createdIntake, HttpStatus.CREATED);
	}

	// DELETE -> Delete Intake -> ONLY ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/intake/{intakeId}")
	public ResponseEntity<ApiResponse> deleteIntake(@PathVariable Integer intakeId) {
		intakeService.deleteIntake(intakeId);
		return new 
				ResponseEntity<ApiResponse>(
				new ApiResponse("Intake with id: " + intakeId + " has been deleted successfully!", true),
				HttpStatus.OK);
	}

}
