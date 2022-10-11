package com.comaecod.jogtracker.services;

import com.comaecod.jogtracker.payloads.IntakeDTO;

public interface IntakeService {
	IntakeDTO createIntake(IntakeDTO dto, Integer jogId);

	void deleteIntake(Integer intakeId);

}
