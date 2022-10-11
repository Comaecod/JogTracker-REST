package com.comaecod.jogtracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.Intake;

public interface IntakeRepo extends JpaRepository<Intake, Integer> {

}
