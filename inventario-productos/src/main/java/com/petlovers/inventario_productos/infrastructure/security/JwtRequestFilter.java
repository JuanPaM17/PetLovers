package com.petlovers.inventario_productos.infrastructure.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;

		if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
			username = jwtTokenUtil.extractUsername(authorizationHeader);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtTokenUtil.validateToken(authorizationHeader)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
						null, Collections.emptyList());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
	}

}
