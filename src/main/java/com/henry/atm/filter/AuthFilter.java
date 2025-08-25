package com.henry.atm.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.henry.atm.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

	private final AuthService authService;

	public AuthFilter(final AuthService authService) {
		this.authService = authService;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			System.out.println("Invalid auth header, skipping authFilter");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);
		try {
			System.out.println("AuthFilter: validating token");
			String customerId = authService.validateToken(token);
			request.setAttribute("customerId", customerId);
			UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(customerId, null, List.of(new SimpleGrantedAuthority("USER")));
			SecurityContextHolder.getContext().setAuthentication(auth);
			System.out.println("AuthFilter: validated token, customer set to " + customerId + " and SecurityContext Auth is: " + SecurityContextHolder.getContext().getAuthentication());
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
