package com.spenceuk.cardealer.dao.repo;

import com.spenceuk.cardealer.dao.entity.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {

}