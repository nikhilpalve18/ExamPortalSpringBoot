package com.exam;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;

@SpringBootApplication
public class ExamPortal implements CommandLineRunner{
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		System.out.println("System is running");
		SpringApplication.run(ExamPortal.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("Starting code...");
			User u = new User();
			u.setFirstname("Nikhil");
			u.setLastname("Palve");
			u.setUsername("nikhilpalve18");
			u.setPassword(bCryptPasswordEncoder.encode("nikhil@12543"));
			u.setEmail("nikhil@gmail.com");
			u.setProfile("default.png");
			u.setPhone("9494399246");
			
			Role role1 = new Role(44L,"ADMIN");
			
			Set<UserRole> st = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role1);
			userRole.setUser(u);
			st.add(userRole);
			
			
			User user1 = this.userService.createUser(u, st);
			System.out.println(user1);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
