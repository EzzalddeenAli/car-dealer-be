package com.spenceuk.cardealer.api.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.spenceuk.cardealer.api.dto.VehicleDto;
import com.spenceuk.cardealer.api.exception.ApiException;
import com.spenceuk.cardealer.service.VehicleService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/vehicles")
public class VehicleController {
  private final VehicleService service;

  /**
   * Get all vehicles in database.
   *
   * @return 200 OK, List of all vehicles.
   */
  @GetMapping
  ResponseEntity<List<VehicleDto>> allVehicles() {
    return ResponseEntity.ok().body(service.all());
  }

  /**
   * Get a single vehicle by ID.
   *
   * @param id vehcleId to get sent via path variable.
   * @return 200 OK, a Vehicle object.
   */
  @GetMapping("{vehicleId}")
  ResponseEntity<VehicleDto> vehicleById(@PathVariable("vehicleId") Long id) {
    return ResponseEntity.ok(service.oneByID(id));
  }

  /**
   * Create a new vehicle.
   *
   * @param newVehicle a Vehicle object to save.
   * @return 200 OK, copy of the new vehicle created.
   */
  @PostMapping
  ResponseEntity<VehicleDto> newVehicle(@RequestBody VehicleDto newVehicle) {
    return ResponseEntity.status(CREATED)
        .body(service.save(newVehicle));
  }

  /**
   * Update a specific vehicles details.
   *
   * @param updateDto Object contaning values to update.
   * @param id vehicle ID to update sent via path variable, must match
   the id sent in the body.
   * @return 200 OK
   */
  @PutMapping("{vehicleId}")
  ResponseEntity<Void> updateVehicle(@RequestBody VehicleDto updateDto,
                                     @PathVariable("vehicleId") Long id) {
    if (id.longValue() != updateDto.getId()) {
      throw ApiException.idMismatch(id.longValue(), updateDto.getId());
    }
    service.update(updateDto);
    return ResponseEntity.status(NO_CONTENT).build();
  }

  /**
   * Delete a vehcile by ID.
   *
   * @param id ID of the vehicle to DELETE.
   * @return 204 NO CONTENT.
   */
  @DeleteMapping("{vehicleId}")
  ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleId") Long id) {
    service.delete(id);
    return ResponseEntity.status(NO_CONTENT).build();
  }
}
