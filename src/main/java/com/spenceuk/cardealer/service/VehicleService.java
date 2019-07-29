package com.spenceuk.cardealer.service;

import com.spenceuk.cardealer.entity.Vehicle;
import com.spenceuk.cardealer.repo.VehicleRepo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class VehicleService {

  private final VehicleRepo repo;

  /**
   * Vehicle Service Constructor.
   *
   * @param repo Vehicle repository
   */
  public VehicleService(VehicleRepo repo) {
    this.repo = repo;
  }

  /**
   * Return all vehicles in database.
   *
   * @return a List of all Vehicles.
   */
  public List<Vehicle> getAllVehicles() {
    return repo.findAll();
  }

  /**
   * Save a new vehicle to the database.
   *
   * @param newVehicle A new Vehicle Object to save.
   * @return A copy of the new saved Vehicle.
   */
  public Vehicle saveVehicle(Vehicle newVehicle) {
    return repo.save(newVehicle);
  }

}