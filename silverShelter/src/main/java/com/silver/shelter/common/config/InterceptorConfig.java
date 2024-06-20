package com.silver.shelter.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.silver.shelter.common.interceptor.BoardTypeInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	
	@Bean
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(boardTypeInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**",
							"/js/**",
							"/images/**",
							"/favicon.ico");
	}
	

	
}
