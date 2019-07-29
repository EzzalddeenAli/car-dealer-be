package com.spenceuk.cardealer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spenceuk.cardealer.entity.Vehicle;
import com.spenceuk.cardealer.service.VehicleService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/vehicles")
public class VehicleController {
  private final VehicleService service;
  private final ObjectMapper mapper;

  /**
   * Get all vehicles in database.
   *
   * @return all vehicles.
   */
  @GetMapping
  public ResponseEntity<String> allVehicles() throws JsonProcessingException {
    String body = mapper.writeValueAsString(service.getAllVehicles());
    return ResponseEntity.ok().body(body);
  }

  /**
   * Create a new vehicle.
   *
   * @param newVehicle a Vehicle object to save.
   * @return copy of the new vehicle created.
   * @throws JsonProcessingException cannot write vehicle to String.
   */
  @PostMapping
  public ResponseEntity<String> newVehicle(@RequestBody Vehicle newVehicle)
      throws JsonProcessingException {
    String body = mapper.writeValueAsString(service.saveVehicle(newVehicle));
    return ResponseEntity.status(201).body(body);
  }
}
