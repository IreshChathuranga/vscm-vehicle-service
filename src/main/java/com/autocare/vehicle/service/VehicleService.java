package com.autocare.vehicle.service;

import com.autocare.vehicle.dto.MileageRequest;
import com.autocare.vehicle.dto.VehicleRequest;
import com.autocare.vehicle.dto.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse create(VehicleRequest r);

    List<VehicleResponse> all();

    VehicleResponse one(Long id);

    VehicleResponse registration(String value);

    List<VehicleResponse> customer(Long id);

    VehicleResponse update(Long id, VehicleRequest r);

    VehicleResponse mileage(Long id, MileageRequest r);

    void delete(Long id);

    String upload(Long id, org.springframework.web.multipart.MultipartFile file) throws java.io.IOException;

    byte[] image(Long id) throws java.io.IOException;
}
