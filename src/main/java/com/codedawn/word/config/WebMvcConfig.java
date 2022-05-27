package com.codedawn.word.config;

import com.codedawn.word.interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 * @author codedawn
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                //配置拦截规则
                .addPathPatterns("/**");
    }


}
