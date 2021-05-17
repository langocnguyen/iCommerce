package com.nabtest.icommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nabtest.icommerce.entity.User;
import com.nabtest.icommerce.repository.UserRepository;

@SpringBootApplication
public class IcommerceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(IcommerceApplication.class); 
	
	public static void main(String[] args) {
		SpringApplication.run(IcommerceApplication.class, args);
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("lnnguyen");
		user.setPassword(passwordEncoder.encode("lnnguyen"));
		userRepository.save(user);
		log.info("An user is created successfully!");
	}
	
}
