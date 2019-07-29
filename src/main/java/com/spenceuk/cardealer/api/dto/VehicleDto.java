package com.spenceuk.cardealer.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
  private long id;
  private String registration;
  private String make;
  private String model;
}