package com.spenceuk.cardealer.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spenceuk.cardealer.api.controller.VehicleController;
import com.spenceuk.cardealer.api.dto.VehicleDto;
import com.spenceuk.cardealer.api.exception.ApiException;
import com.spenceuk.cardealer.config.GlobalExceptionHandler;
import com.spenceuk.cardealer.service.VehicleService;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(RestDocumentationExtension.class)
public class VehicleControllerTests {
  private static final String BASE_URL = "/api/vehicles";
  private static final String VEH_URL = BASE_URL + "/{vehicleId}";
  private static final long ONE = 1L;

  private MockMvc mvc;
  private VehicleController controller;
  @Mock private VehicleService service;
  private VehicleDto testVehicle;
  private ObjectMapper mapper;

  /**
   * VehicleControllerTests setup.
   */
  @BeforeEach
  public void setup(RestDocumentationContextProvider contextProvider) {
    initMocks(this);
    controller = new VehicleController(service);
    testVehicle = testVehicleDto(ONE);
    mapper = new ObjectMapper();
    mvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new GlobalExceptionHandler())
        .apply(MockMvcRestDocumentation.documentationConfiguration(contextProvider))
        .build();
  }

  /**
   * teardown method.
   */
  @AfterEach
  public void teardown() {
    verifyNoMoreInteractions(service);
  }

  @Test
  @DisplayName("DELETE /api/vehicles/{id} throws ID NOT FOUND")
  void deleteVehicleException() throws Exception {
    doThrow(ApiException.idNotFound(ONE)).when(service).delete(anyLong());
    mvc.perform(delete(VEH_URL, ONE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.status", is(404)))
        .andExpect(jsonPath("$.error", is("Not Found: Cannot find ID: 1")));
    then(service).should().delete(anyLong());
  }

  @Test
  @DisplayName("DELETE /api/vehicles/{id}")
  void deleteVehicle() throws Exception {
    doNothing().when(service).delete(anyLong());
    mvc.perform(delete(VEH_URL, ONE))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotHaveJsonPath());
    then(service).should().delete(anyLong());
  }

  @Test
  @DisplayName("PUT /api/vehicles/[id} throws ID NOT FOUND")
  void updateVehicleIdNotFound() throws Exception {
    doThrow(ApiException.idNotFound(ONE)).when(service).update(any());
    var body = testVehicleDto(ONE);
    mvc.perform(put(VEH_URL, ONE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(body)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.status", is(404)))
        .andExpect(jsonPath("$.error", is("Not Found: Cannot find ID: 1")));
    then(service).should().update(any());

  }

  @Test
  @DisplayName("PUT /api/vehicles/[id} throws ID MIS MATCH")
  void updateVehicleIdMismatch() throws Exception {
    var body = testVehicleDto(2L);
    mvc.perform(put(VEH_URL, ONE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(body)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.error",
            is("Bad Request: ID's do not match: Path id: 1 != Object: id 2")));
  }

  @Test
  @DisplayName("PUT /api/vehicles/[id}")
  void updateVehicle() throws Exception {
    doNothing().when(service).update(any(VehicleDto.class));
    var body = testVehicleDto(ONE);
    mvc.perform(put(VEH_URL, ONE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(body)))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotHaveJsonPath());
    then(service).should().update(any(VehicleDto.class));
  }

  @Test
  @DisplayName("POST /api/vehicles")
  void newVehicle() throws Exception {
    given(service.save(any(VehicleDto.class))).willReturn(testVehicle);
    var body = testVehicleDto(0);
    mvc.perform(post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(body)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.make", is("make")))
        .andExpect(jsonPath("$.model", is("model")))
        .andExpect(jsonPath("$.registration", is("registration")));
    then(service).should().save(any());
  }

  @Test
  @DisplayName("GET /api/vehicles")
  void vehiclesGet() throws Exception {
    given(service.all()).willReturn(List.of(testVehicle));
    mvc.perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].make", is("make")))
        .andExpect(jsonPath("$[0].model", is("model")))
        .andExpect(jsonPath("$[0].registration", is("registration")))
        .andDo(MockMvcRestDocumentation.document("GETVehicles"));
    then(service).should().all();
  }

  @Test
  @DisplayName("GET /api/vehicles/{id} throws ID NOT FOUND")
  void vehicleByIdNotFoundException() throws Exception {
    given(service.oneByID(ONE)).willThrow(ApiException.idNotFound(ONE));
    mvc.perform(get(VEH_URL, ONE))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.status", is(404)))
        .andExpect(jsonPath("$.error", is("Not Found: Cannot find ID: 1")));
    then(service).should().oneByID(ONE);
  }

  @Test
  @DisplayName("GET /api/vehicles/{id}")
  void vehicleById() throws Exception {
    given(service.oneByID(ONE)).willReturn(testVehicle);
    mvc.perform(get(VEH_URL, ONE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.make", is("make")))
        .andExpect(jsonPath("$.model", is("model")))
        .andExpect(jsonPath("$.registration", is("registration")));
    then(service).should().oneByID(ONE);
  }

  private VehicleDto testVehicleDto(long id) {
    var vehicle = new VehicleDto();
    vehicle.setId(id);
    vehicle.setMake("make");
    vehicle.setModel("model");
    vehicle.setRegistration("registration");
    return vehicle;
  }
}
