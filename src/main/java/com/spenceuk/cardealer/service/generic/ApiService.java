package com.spenceuk.cardealer.service.generic;

import java.util.List;

interface ApiService<T, I> {

  public List<T> all();

  public T oneByID(I id);

  public T save(T newVehicle);

  public void update(T updateDto);

  public void delete(I id);
}