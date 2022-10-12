package com.comaecod.jogtracker;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.comaecod.jogtracker.config.AppConstantsDefaults;
import com.comaecod.jogtracker.entities.Role;
import com.comaecod.jogtracker.repositories.RoleRepo;

@SpringBootApplication
public class JogTrackerApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(JogTrackerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("@comaecod: " + passwordEncoder.encode("Password@01"));
		try {
			Role role1 = new Role();
			role1.setId(AppConstantsDefaults.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstantsDefaults.ROLE_USER);
			role2.setName("ROLE_USER");

			List<Role> roles = List.of(role1, role2);
			
			List<Role> result = roleRepo.saveAll(roles);

//			result.forEach(r -> System.out.println(r.getName()));

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
