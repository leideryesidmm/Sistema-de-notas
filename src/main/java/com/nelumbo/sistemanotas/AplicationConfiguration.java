package com.nelumbo.sistemanotas;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AplicationConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
