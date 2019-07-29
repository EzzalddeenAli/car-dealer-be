package com.spenceuk.cardealer.api.controller;

import static com.spenceuk.cardealer.api.exception.ApiExceptions.ID_MISMATCH;
import static org.springframework.http.HttpStatus.CREATED;

import com.spenceuk.cardealer.api.dto.VehicleDto;
import com.spenceuk.cardealer.api.exception.ApiException;
import com.spenceuk.cardealer.service.VehicleService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
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
   * @return all vehicles.
   */
  @GetMapping
  ResponseEntity<List<VehicleDto>> allVehicles() {
    return ResponseEntity.ok().body(service.allVehiclesDto());
  }

  /**
   * Create a new vehicle.
   *
   * @param newVehicle a Vehicle object to save.
   * @return copy of the new vehicle created.
   */
  @PostMapping
  ResponseEntity<VehicleDto> newVehicle(@RequestBody VehicleDto newVehicle) {
    return ResponseEntity.status(CREATED)
        .body(service.saveVehicle(newVehicle));
  }

  /**
   * Update a specific vehicles details.
   * @param updateDto Object contaning values to update.
   * @return 200 OK
   */
  @PutMapping("/{id}")
  ResponseEntity<Void> updateVehicle(@RequestBody VehicleDto updateDto,
                                     @PathVariable("id") Long id) {

    if (id.longValue() != updateDto.getId()) {
      throw ApiException.idMismatch(id.longValue(), updateDto.getId());
    }
    service.updateAll(updateDto);
    return ResponseEntity.ok().build();
  }

}
