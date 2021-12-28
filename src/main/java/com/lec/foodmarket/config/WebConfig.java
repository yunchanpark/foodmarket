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
			.addResourceHandler("/ckUpload/productImages/detail/**")
			.addResourceLocations("file:///C:/spring_foodmarket/ckupload/productImages/detail/")
			;	
		registry
			.addResourceHandler("/ckUpload/productImages/product/**")
			.addResourceLocations("file:///C:/spring_foodmarket/ckupload/productImages/product/")
			;	
		registry
			.addResourceHandler("/ckUpload/inquiryImages/**")
			.addResourceLocations("file:///D:/spring_foodmarket/ckupload/inquiryImages/")
			;	
		registry
			.addResourceHandler("/ckUpload/memberImages/**")
			.addResourceLocations("file:///C:/spring_foodmarket/ckupload/memberImages/")
			;	
		registry
			.addResourceHandler("/ckUpload/refundImages/**")
			.addResourceLocations("file:///C:/spring_foodmarket/ckupload/refundImages/")
			;	
	}
}
