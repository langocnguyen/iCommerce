package com.nabtest.icommerce.token;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nabtest.icommerce.service.UserServiceImpl;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
		try {
			// Get Jwt from request
			String jwt = getJwtFromRequest(request);
			
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String username = tokenProvider.getUserNameFromJWT(jwt);
				UserDetails userDetails = userService.loadUserByUsername(username);
				
				if(userDetails != null) {
					UsernamePasswordAuthenticationToken
					authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			log.error("Failed on set user authentication ", ex);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
