package com.Kackan.multitenancy_demo.controller;

import com.Kackan.multitenancy_demo.CarMapper;
import com.Kackan.multitenancy_demo.dto.CarDTO;
import com.Kackan.multitenancy_demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getCars() {
        return new ResponseEntity<>(carMapper.convertEntityListToDTOList(carService.getCars()), HttpStatus.OK) ;
    }

}
