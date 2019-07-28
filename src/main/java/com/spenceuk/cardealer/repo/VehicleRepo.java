package com.spenceuk.cardealer.repo;

import com.spenceuk.cardealer.entity.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

}