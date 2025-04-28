package com.Kackan.multitenancy_demo;

import com.Kackan.multitenancy_demo.dto.CarDTO;
import com.Kackan.multitenancy_demo.model.Car;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {
    public CarDTO convertEntityToDTO(Car car) {
        if (car == null) {
            return null;
        }
        return new CarDTO(car.getBrand(), car.getColor());
    }

    public List<CarDTO> convertEntityListToDTOList(List<Car> carList) {
        List<CarDTO> carDTOList = new ArrayList<>();
        if (carList == null
                || carList.size() == 0) {
            return carDTOList;
        }

        return carList.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }
}
