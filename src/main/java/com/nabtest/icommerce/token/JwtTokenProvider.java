package com.nabtest.icommerce.token;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nabtest.icommerce.dto.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	private static final String JWT_SECRET = "secret_key";
	private static final long JWT_EXPIRATION = 604800000L;
	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	// Generate token from user details
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		
		return Jwts.builder()
				.setSubject(userDetails.getUser().getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}
	
	// Get username from jwt token
	public String getUserNameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(JWT_SECRET)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {
			log.info("Invalid JWT token", ex);
		} catch (ExpiredJwtException ex) {
			log.info("Expired JWT token", ex);
		} catch (UnsupportedJwtException ex) {
			log.info("Unsupported JWT token", ex);
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims string is empty.", ex);
		}
		return false;
	}

}
