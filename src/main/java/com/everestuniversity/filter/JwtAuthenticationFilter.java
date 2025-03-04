package com.everestuniversity.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.everestuniversity.service.JwtService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter implements Filter {

	@Autowired
	private JwtService jwtService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getRequestURI();
		System.out.println("URL from filter: " + url);

		// **Step 1: Skip authentication for public endpoints**
		if (isPublicEndpoint(url)) {
			System.out.println("Skipping JWT Authentication for public endpoint: " + url);
			chain.doFilter(request, response);
			return;
		} else {
			String token = req.getHeader("Authorization");

			if (token == null || !token.startsWith("Bearer ")) {
				sendErrorResponse(res, "Missing or invalid Authorization header");
				return;
			}

			// **Step 3: Remove 'Bearer ' prefix and validate token**
			token = token.substring(7);
			System.out.println("Token from filter: " + token);

			if (!jwtService.validateToken(token)) {
				sendErrorResponse(res, "Invalid or expired token");
				return;
			}

			// **Step 4: Extract email from the token**
			String email = jwtService.getEmailFromToken(token);

			// **Step 5: Authenticate user without roles**
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null,
					Collections.emptyList());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			System.out.println("Authenticated User: " + email);

			// **Step 6: Proceed with the request**
			chain.doFilter(request, response);

		}
	}

	/**
	 * Define public endpoints that don't require authentication
	 */
	private boolean isPublicEndpoint(String url) {
		return url.contains("/public/") || url.contains("/v3/") || url.contains("/swagger-ui/")
				|| url.contains("/swagger-resources/") || url.contains("/webjars/") || url.contains("/generate-token");
	}

	/**
	 * Sends an Unauthorized response if authentication fails
	 */
	private void sendErrorResponse(HttpServletResponse res, String message) throws IOException {
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		res.setContentType("application/json");
		res.getWriter().write("{\"error\": \"" + message + "\"}");
	}
}
