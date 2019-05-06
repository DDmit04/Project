package com.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    @Value("${upload.path.userImages}")
	private String userImagesUploadPath;

	@Value("${upload.path.groupImages}")
	private String groupImagesUploadPath;
	
	@Value("${upload.path.chatImages}")
	private String chatImagesUploadPath;
	
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
        registry.addResourceHandler("/imgUserPic/**")
        		.addResourceLocations("file:/" + userImagesUploadPath + "/");
        
        registry.addResourceHandler("/imgChatPic/**")
				.addResourceLocations("file:/" + chatImagesUploadPath + "/");
        
        registry.addResourceHandler("/imgGroupPic/**")
				.addResourceLocations("file:/" + groupImagesUploadPath + "/");
        
        registry.addResourceHandler("/static/**")
        		.addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");
        
        registry.addResourceHandler("/customJs/**")
        		.addResourceLocations("classpath:/customJs/");
    }
}
