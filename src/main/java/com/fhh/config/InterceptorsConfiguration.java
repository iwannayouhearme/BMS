package com.fhh.config;

import com.fhh.interceptors.DOInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能描述：（拦截器配置类）
 * @author: biubiubiu小浩
 * @date: 2018-10-08 21:32
 */
@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {

    @Bean
    public DOInterceptors doInterceptors(){
        return  new DOInterceptors();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(doInterceptors()).addPathPatterns("/**")
                .excludePathPatterns("/bms/user/login");
    }
}
