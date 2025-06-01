package com.rzq.week_1_3_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
		UserDetails userDetails = User
				.withUsername("user")
				.password(encoder.encode("user123"))
				.roles("USER")
				.build();
		
		UserDetails adminDetails = User
				.withUsername("admin")
				.password(encoder.encode("admin123"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(userDetails, adminDetails);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeHttpRequests(
				req -> req
				.antMatchers("/api/public").permitAll()
				.antMatchers("/api/admin").hasRole("ADMIN")
				.antMatchers("/api/user").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
