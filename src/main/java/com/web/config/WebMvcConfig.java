package com.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    @Value("${upload.path.posts}")
    private String uploadPathPosts;
    
    @Value("${upload.path.userPics}")
    private String uploadPathUserPics;
    
//    @Bean
//    public RestTemplate getRestTemplate() {
//    	return new RestTemplate();
//    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/" + uploadPathPosts + "/");
        
        registry.addResourceHandler("/imgUserPic/**")
        		.addResourceLocations("file:/" + uploadPathUserPics + "/");
        
        registry.addResourceHandler("/static/**")
        		.addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");
        
        registry.addResourceHandler("/customJs/**")
        		.addResourceLocations("classpath:/customJs/");
    }
}
