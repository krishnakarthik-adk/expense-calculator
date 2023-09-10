package com.kk.expensecalculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExpenseCalculatorCorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedMethods("OPTIONS", "GET", "PUT", "POST")
			.allowedOrigins("http://expensecalculator-ui.s3-website.ap-south-1.amazonaws.com")
			.allowedHeaders("*");
	}
}
