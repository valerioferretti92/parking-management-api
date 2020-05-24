package com.valerioferretti.parking.security;

import com.valerioferretti.parking.config.JwtConfig;
import com.valerioferretti.parking.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.valerioferretti.parking.security.SecurityConstants.*;

@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserProfileService userProfileService;

	private JwtConfig jwtConfig;

	@Autowired
	public WebSecurityConfig(UserProfileService userProfileService,
							 JwtConfig jwtConfig){
		this.userProfileService = userProfileService;
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTAuthorizationFilter jwtAuthorizationFilter;
		JWTAuthenticationFilter jwtAuthenticationFilter;

		jwtAuthorizationFilter = new JWTAuthorizationFilter(
				authenticationManager(),
				userProfileService,
				jwtConfig);
		jwtAuthenticationFilter = new JWTAuthenticationFilter(
				authenticationManager(),
				userProfileService,
				jwtConfig);
		jwtAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);

		http.cors().and().csrf().disable().authorizeRequests()
				// Swagger, permit all
				.antMatchers(ADMIN_SIGNUP_URL, USER_SIGNUP_URL, LOGIN_URL).permitAll()
				.anyRequest().authenticated().and()
				.addFilter(jwtAuthenticationFilter)
				.addFilter(jwtAuthorizationFilter)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService((UserDetailsService) userProfileService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.applyPermitDefaultValues();
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE"));
		corsConfiguration.setExposedHeaders(Arrays.asList(SecurityConstants.JWT_HEADER_KEY));
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
