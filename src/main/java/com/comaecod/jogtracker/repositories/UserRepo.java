/**
 * 
 */
package com.comaecod.jogtracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.User;

public interface UserRepo extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);
}
