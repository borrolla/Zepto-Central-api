package com.central.zepto.central_api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();

    }
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
