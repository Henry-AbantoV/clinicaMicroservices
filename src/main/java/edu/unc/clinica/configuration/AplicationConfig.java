package edu.unc.clinica.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AplicationConfig {
	 @Bean
	   /* public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }*/
	 public ModelMapper modelMapper() {
		 return new ModelMapper();
	 }
}
