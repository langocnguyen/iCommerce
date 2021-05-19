package com.nabtest.icommerce.token;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import io.jsonwebtoken.SignatureException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.nabtest.icommerce.dto.CustomUserDetails;
import com.nabtest.icommerce.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenProviderTest {
	
	Logger logger = LoggerFactory.getLogger(JwtTokenProviderTest.class);
	
	@Autowired(required = true)
	private JwtTokenProvider tokenProvider;
	
	@Test
	public void generateToken_Successfully() {
		User user = new User();
		user.setId(12L);
		user.setUsername("admin");
		CustomUserDetails userDetail = new CustomUserDetails(user);
		String token = tokenProvider.generateToken(userDetail);
		logger.info(token);
	}
	
	@Test
	public void validateToken_GiveTheValidToken_ValidatePassed() {
		User user = new User();
		user.setId(12L);
		user.setUsername("admin");
		CustomUserDetails userDetail = new CustomUserDetails(user);
		String token = tokenProvider.generateToken(userDetail);
		
		boolean isValid = tokenProvider.validateToken(token);
		assertTrue(isValid);
	}
	
	@Test
	public void GiveTheInValidToken_ValidateDoNotPassed_SignatureException() {
		assertThrows(SignatureException.class, () -> {
			tokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjExIiwiaWF0IjoxNjE0ODQzOTA3LCJleHAiOjE2MTU0NDg3MDd9.jKxeDOEofllToVlllMn1IMsrvWN_F_IdPYpE657Qba-l9xUVyWc3clyzVg");
		});
	}
}