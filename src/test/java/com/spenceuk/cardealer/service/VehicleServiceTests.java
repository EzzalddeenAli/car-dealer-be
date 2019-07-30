package com.spenceuk.cardealer.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.spenceuk.cardealer.api.dto.VehicleDto;
import com.spenceuk.cardealer.api.exception.ApiException;
import com.spenceuk.cardealer.dao.entity.Vehicle;
import com.spenceuk.cardealer.dao.repo.VehicleRepo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.modelmapper.ModelMapper;


public class VehicleServiceTests {
  private static final long ONE = 1L;
  private static final long TWO = 2L;

  @Mock private VehicleRepo repo;
  private ModelMapper mapper = new ModelMapper();
  private VehicleService service;

  /**
   * VehicleServiceTests setup.
   */
  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
    service = new VehicleService(repo, mapper);
  }

  /**
   * Verify no more interactions.
   */
  @AfterEach
  void finish() {
    Mockito.verifyNoMoreInteractions(repo);
  }

  @Test
  void oneByIdTest() {
    given(repo.findById(TWO)).willReturn(Optional.of(testVehicle()));
    var actual = service.oneByID(TWO);
    then(repo).should().findById(TWO);
    assertThat("vehicle ID is 2L", actual.getId(), is(equalTo(2L)));
  }

  @Test
  @DisplayName("all returns all vehicles")
  void allTest() {
    given(repo.findAll()).willReturn(List.of(testVehicle()));
    var actual = service.all();
    then(repo).should().findAll();
    assertThat("List is not empty", actual, is(not(empty())));
    assertThat("contains one object", actual, hasSize(1));
    assertThat("vehicle has id 2L", actual.get(0).getId(), is(equalTo(2L)));
  }

  @Test
  @DisplayName("saves new vehicles")
  void saveTest() {
    given(repo.save(any(Vehicle.class))).willReturn(testVehicle());
    var actual = service.save(testVehicleDto());
    then(repo).should().save(any(Vehicle.class));
    assertThat("Saved Vehicle is returned", actual.getId(), is(equalTo(2L)));
  }

  @ParameterizedTest
  @ValueSource(longs = {0L, -1L, -0L, +0L})
  @DisplayName("UPDATE Throws exception if ID <= 0")
  void updateIdExceptionTest(long id) {
    given(repo.existsById(anyLong())).willReturn(true);
    var testVehicle = testVehicleDto();
    testVehicle.setId(id);
    assertThrows(ApiException.class, () -> service.update(testVehicle));
  }

  @Test
  @DisplayName("UPDATE Throws exception if ID does not exist")
  void updateNoIdExceptionTest() {
    given(repo.existsById(anyLong())).willReturn(false);
    assertThrows(ApiException.class, () -> service.update(testVehicleDto()));
    then(repo).should().existsById(ONE);
  }

  @Test
  @DisplayName("Updates if exists")
  void updateTest() {
    given(repo.existsById(anyLong())).willReturn(true);
    service.update(testVehicleDto());
    then(repo).should().existsById(ONE);
    then(repo).should().save(any(Vehicle.class));
  }

  @Test
  @DisplayName("Deletes if ID exists")
  void deleteByIdTest() {
    given(repo.existsById(anyLong())).willReturn(true);
    service.delete(ONE);
    then(repo).should().existsById(ONE);
    then(repo).should().deleteById(ONE);
  }

  @Test
  @DisplayName("DELETE Throws exception if ID does not exist")
  void deleteByIdExceptionTest() {
    given(repo.existsById(anyLong())).willReturn(false);
    assertThrows(ApiException.class, () -> service.delete(ONE));
    then(repo).should().existsById(ONE);
  }

  private Vehicle testVehicle() {
    var vehicle = new Vehicle();
    vehicle.setId(TWO);
    return vehicle;
  }

  private VehicleDto testVehicleDto() {
    var vehicle = new VehicleDto();
    vehicle.setId(ONE);
    return vehicle;
  }
}