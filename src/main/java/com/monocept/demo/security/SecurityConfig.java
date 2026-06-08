package com.monocept.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/api/auth/register", "/api/auth/login", "/swagger-ui.html", "/swagger-ui/**",
								"/v3/api-docs/**")
						.permitAll()

						.requestMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN", "AGENT")

						// PRODUCTS
						.requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("CUSTOMER", "ADMIN", "AGENT")

						.requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

						// POLICY PLANS
						.requestMatchers(HttpMethod.GET, "/api/plans/**").hasAnyRole("CUSTOMER", "ADMIN", "AGENT")

						.requestMatchers(HttpMethod.POST, "/api/plans/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.PUT, "/api/plans/**").hasRole("ADMIN")

						.requestMatchers(HttpMethod.DELETE, "/api/plans/**").hasRole("ADMIN")

						// POLICIES
						.requestMatchers(HttpMethod.POST, "/api/policies/**").hasRole("CUSTOMER")

						.requestMatchers(HttpMethod.GET, "/api/policies/**").hasAnyRole("CUSTOMER", "ADMIN", "AGENT")

						// PAYMENTS
						.requestMatchers(HttpMethod.POST, "/api/payments/**").hasRole("CUSTOMER")

						.requestMatchers(HttpMethod.GET, "/api/payments/**").hasAnyRole("CUSTOMER", "ADMIN")

						.requestMatchers(HttpMethod.PUT, "/api/payments/**").hasRole("ADMIN")

						// CLAIMS
						.requestMatchers(HttpMethod.POST, "/api/claims/**").hasRole("CUSTOMER")

						.requestMatchers(HttpMethod.GET, "/api/claims/**").hasAnyRole("CUSTOMER", "ADMIN", "AGENT")

						.requestMatchers(HttpMethod.PUT, "/api/claims/*/review").hasRole("AGENT")

						.requestMatchers(HttpMethod.PUT, "/api/claims/*/recommend-approval").hasRole("AGENT")

						.requestMatchers(HttpMethod.PUT, "/api/claims/*/recommend-rejection").hasRole("AGENT")

						.requestMatchers(HttpMethod.PUT, "/api/claims/*/approve").hasRole("ADMIN")

						.requestMatchers(HttpMethod.PUT, "/api/claims/*/reject").hasRole("ADMIN")

						// CLAIM HISTORY
						.requestMatchers(HttpMethod.GET, "/api/claim-history/**").hasAnyRole("ADMIN", "AGENT")

						.anyRequest().authenticated())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}
}