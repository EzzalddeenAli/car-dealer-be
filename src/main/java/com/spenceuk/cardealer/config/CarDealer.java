package com.spenceuk.cardealer.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarDealer {

  /**
   * ModelMapper for Autowiring.
   */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}