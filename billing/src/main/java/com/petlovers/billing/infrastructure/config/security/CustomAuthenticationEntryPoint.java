package com.petlovers.billing.infrastructure.config.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final String UNAUTHORIZED_MESSAGE = "{" + "\"success\": false, " + "\"message\": \"Unauthorized\", "
			+ "\"statusCode\": \"600\"" + "}";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(UNAUTHORIZED_MESSAGE);
	}
}
