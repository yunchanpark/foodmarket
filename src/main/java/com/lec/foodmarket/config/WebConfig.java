package com.lec.foodmarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		/*
		 * EX)
		 * 	/ckUpload/inquiryImages/으로 시작하는 URI호출은
		 * 	/ckUpload/inquiryIages/ 경로 하위에 있는 리소스 파일이다라는 의미
		 */  
		registry
			.addResourceHandler("/ckUpload/inquiryImages/**")
			.addResourceLocations("/ckUpload/inquiryImages/")
			;
		
		registry
			.addResourceHandler("/ckUpload/productImages/**")
			.addResourceLocations("/ckUpload/productImages/")
			;	
		
		registry
			.addResourceHandler("/ckUpload/refundImages/**")
			.addResourceLocations("/ckUpload/refundImages/")
			;
	}
}
