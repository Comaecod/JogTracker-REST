/**
 * 
 */
package com.comaecod.jogtracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.User;

/**
 * @author vishn
 *
 */
public interface UserRepo extends JpaRepository<User, String> {

}
