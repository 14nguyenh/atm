package com.henry.atm.service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	private static final String SECRET = "superity-duperity-secrety-ultra-mega-secret-key"; // store and retrieve from k8s secrets in production
	private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
	private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

	public String generateToken(final String customerId) {
		return Jwts.builder()
				.setSubject(customerId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME.toMillis()))
				.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
				.compact();
	}

	public String validateToken(final String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
