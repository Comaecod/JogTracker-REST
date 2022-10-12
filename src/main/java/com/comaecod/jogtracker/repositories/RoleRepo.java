package com.comaecod.jogtracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
