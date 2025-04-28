package com.Kackan.multitenancy_demo.service;

import com.Kackan.multitenancy_demo.model.Car;
import com.Kackan.multitenancy_demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(UUID id) {
        return carRepository.findById(id)
                .orElse(null);
    }
}
