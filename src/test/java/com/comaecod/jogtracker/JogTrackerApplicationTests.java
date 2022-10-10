package com.comaecod.jogtracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.comaecod.jogtracker.repositories.UserRepo;

@SpringBootTest
class JogTrackerApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	public void repoTest() {
		String className = userRepo.getClass().getName();
		String packageName = userRepo.getClass().getPackageName();
		System.out.println("@comaecod" + className);
		System.out.println("@comaecod" + packageName);
	}

}
