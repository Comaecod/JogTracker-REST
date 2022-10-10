package com.comaecod.jogtracker.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.Category;
import com.comaecod.jogtracker.entities.Jog;
import com.comaecod.jogtracker.entities.User;

public interface JogRepo extends JpaRepository<Jog, Integer> {
	List<Jog> findByUser(User user);

	List<Jog> findByCategory(Category category);

}
