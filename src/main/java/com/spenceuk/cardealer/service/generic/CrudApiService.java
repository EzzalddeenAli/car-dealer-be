package com.spenceuk.cardealer.service.generic;

import com.spenceuk.cardealer.api.exception.ApiException;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO complete this abstraction with mappers for use in Controllers.

class CrudApiService<T, R extends JpaRepository<T,Long>, S> implements ApiService<T, Long> {
  protected final Class<T> entity;
  protected final R repo;
  protected final Class<S> dto;

  public CrudApiService(Class<T> entity, R repo, Class<S> dto) {
    this.entity = entity;
    this.repo = repo;
    this.dto = dto;
  }

  /**
   * Find one Entity by it's ID.
   */
  @Override
  public T oneByID(Long id) {
    return repo.findById(id).orElseThrow(
        () -> ApiException.idNotFound(id.longValue()));
  }

  @Override
  public List<T> all() {
    return repo.findAll();
  }

  @Override
  public T save(T newVehicle) {
    return null;
  }

  @Override
  public void update(T updateDto) {

  }

  @Override
  public void delete(Long id) {

  }

  private List<T> allVehicles() {
    return repo.findAll();
  }

}