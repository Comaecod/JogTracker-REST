package com.comaecod.jogtracker.services.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comaecod.jogtracker.entities.Intake;
import com.comaecod.jogtracker.entities.Jog;
import com.comaecod.jogtracker.exceptions.ResourceNotFoundException;
import com.comaecod.jogtracker.payloads.IntakeDTO;
import com.comaecod.jogtracker.repositories.IntakeRepo;
import com.comaecod.jogtracker.repositories.JogRepo;
import com.comaecod.jogtracker.services.IntakeService;

@Service
public class IntakeServiceImpl implements IntakeService {

	@Autowired
	private JogRepo jogRepo;

	@Autowired
	private IntakeRepo intakeRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public IntakeDTO createIntake(IntakeDTO dto, Integer jogId) {
		Jog jog = jogRepo.findById(jogId)
				.orElseThrow(() -> new ResourceNotFoundException("Intake", "id", Integer.toString(jogId)));
		Intake intake = modelMapper.map(dto, Intake.class);
		intake.setJog(jog);
		intake.setDate(new Date());
		Intake savedIntake = intakeRepo.save(intake);
		return modelMapper.map(savedIntake, IntakeDTO.class);
	}

	@Override
	public void deleteIntake(Integer intakeId) {
		Intake intake = intakeRepo.findById(intakeId)
				.orElseThrow(() -> new ResourceNotFoundException("Intake", "id", Integer.toString(intakeId)));
		intakeRepo.delete(intake);
	}

}
