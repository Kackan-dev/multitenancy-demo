package com.Kackan.multitenancy_demo.service;

import com.Kackan.multitenancy_demo.model.Car;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<Car> getCars();
    Car getCarById(UUID id);
}
