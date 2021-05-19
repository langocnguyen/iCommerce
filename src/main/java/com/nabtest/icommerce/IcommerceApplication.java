package com.nabtest.icommerce;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nabtest.icommerce.entity.Role;
import com.nabtest.icommerce.entity.User;
import com.nabtest.icommerce.enums.UserRole;
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
		User user1 = new User();
		user1.setUsername("admin");
		user1.setPassword(passwordEncoder.encode("admin"));
		Set<Role> roles1 = new HashSet<>();
		roles1.add(new Role(UserRole.ROLE_ADMIN.getCode()));
		user1.setRoles(roles1);
		user1.setEnabled(true);
		
		userRepository.save(user1);
		log.info("An user is created successfully - " + user1.getUsername());
		
		User user2 = new User();
		user2.setUsername("customer");
		user2.setPassword(passwordEncoder.encode("customer"));
		Set<Role> roles2 = new HashSet<>();
		roles2.add(new Role(UserRole.ROLE_CUSTOMER.getCode()));
		user2.setRoles(roles2);
		user2.setEnabled(true);
		
		userRepository.save(user2);
		log.info("An user is created successfully - " + user2.getUsername());
	}
	
}
