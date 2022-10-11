package com.comaecod.jogtracker.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.comaecod.jogtracker.entities.Category;
import com.comaecod.jogtracker.entities.Jog;
import com.comaecod.jogtracker.entities.User;

public interface JogRepo extends JpaRepository<Jog, Integer> {
	Page<Jog> findByUser(User user, Pageable pageable);

	List<Jog> findByCategory(Category category);

	// This defect has been fixed now in hibernate-core.
	// @Query("select j from jogging_data j where j.location like :keyword")
	// List<Jog> findByLocationContaining(@Param("keyword") String location);

	List<Jog> findByLocationContaining(String location);
}
