/*
 * @file AplicationConfig.java;
 * @Autor Henry AV (c)2024
 * @Created 4 mar 2024,8:14:05
 */
package edu.unc.clinica.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class AplicationConfig.
 */
@Configuration
public class AplicationConfig {
	 
 	/**
 	 * Model mapper.
 	 *
 	 * @return the model mapper
 	 */
 	@Bean
	   
	 public ModelMapper modelMapper() {
		 return new ModelMapper();
	 }
}
