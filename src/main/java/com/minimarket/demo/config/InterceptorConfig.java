package com.minimarket.demo.config;

import com.minimarket.demo.interceptor.EmpleadoLogeadoInteceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

    
    String[] rutasExcluidas = new String[]{
        "/adminlte/**","/calendario/**","/dist/**","/gentella/**","/libs/**","/plugins/**","/select2/**",
        "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**","/uicons-regular-rounded/**",
        "/logearse","/login","/stylebonis.css"
        };

    @Override
    public void addInterceptors(final InterceptorRegistry registry){
         

        registry.addInterceptor(new EmpleadoLogeadoInteceptor())
            .excludePathPatterns(rutasExcluidas);

    }

}
