package com.autocare.vehicle.service;

import com.autocare.vehicle.dto.MileageRequest;
import com.autocare.vehicle.dto.VehicleRequest;
import com.autocare.vehicle.dto.VehicleResponse;
import com.autocare.vehicle.entity.Vehicle;
import com.autocare.vehicle.exception.DuplicateResourceException;
import com.autocare.vehicle.exception.ResourceNotFoundException;
import com.autocare.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository repo;
    private final RestClient http;
    private final StorageService storage;

    public VehicleServiceImpl(VehicleRepository r, RestClient.Builder b, StorageService s) {
        repo = r;
        http = b.build();
        storage = s;
    }

    public VehicleResponse create(VehicleRequest r) {
        validateCustomer(r.customerId());
        if (repo.existsByRegistrationNumber(r.registrationNumber()))
            throw new DuplicateResourceException("Registration already exists");
        Vehicle v = new Vehicle();
        apply(v, r);
        v.setVehicleCode("VEH-%06d".formatted(repo.count() + 1));
        return out(repo.save(v));
    }

    public List<VehicleResponse> all() {
        return repo.findAll().stream().map(this::out).toList();
    }

    public VehicleResponse one(Long id) {
        return out(get(id));
    }

    public VehicleResponse registration(String n) {
        return repo.findByRegistrationNumber(n).map(this::out).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + n));
    }

    public List<VehicleResponse> customer(Long id) {
        return repo.findByCustomerId(id).stream().map(this::out).toList();
    }

    public VehicleResponse update(Long id, VehicleRequest r) {
        Vehicle v = get(id);
        validateCustomer(r.customerId());
        if (!v.getRegistrationNumber().equals(r.registrationNumber()) && repo.existsByRegistrationNumber(r.registrationNumber()))
            throw new DuplicateResourceException("Registration already exists");
        apply(v, r);
        return out(repo.save(v));
    }

    public VehicleResponse mileage(Long id, MileageRequest r) {
        Vehicle v = get(id);
        if (r.mileage() < v.getCurrentMileage())
            throw new IllegalArgumentException("Mileage cannot be lower than current mileage");
        v.setCurrentMileage(r.mileage());
        return out(repo.save(v));
    }

    public void delete(Long id) {
        repo.delete(get(id));
    }

    public String upload(Long id, MultipartFile f) throws IOException {
        Vehicle v = get(id);
        String path = storage.upload(f);
        v.setVehicleImagePath(path);
        repo.save(v);
        return path;
    }

    public byte[] image(Long id) throws IOException {
        Vehicle v = get(id);
        if (v.getVehicleImagePath() == null) throw new ResourceNotFoundException("Vehicle has no image");
        return storage.download(v.getVehicleImagePath());
    }

    private void validateCustomer(Long id) {
        try {
            http.get().uri("http://CUSTOMER-SERVICE/api/v1/customers/{id}", id).retrieve().toBodilessEntity();
        } catch (Exception e) {
            throw new IllegalArgumentException("Customer does not exist: " + id);
        }
    }

    private Vehicle get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
    }

    private void apply(Vehicle v, VehicleRequest r) {
        v.setCustomerId(r.customerId());
        v.setRegistrationNumber(r.registrationNumber());
        v.setManufacturer(r.manufacturer());
        v.setModel(r.model());
        v.setManufacturedYear(r.manufacturedYear());
        v.setFuelType(r.fuelType());
        v.setTransmissionType(r.transmissionType());
        v.setEngineNumber(r.engineNumber());
        v.setChassisNumber(r.chassisNumber());
        v.setCurrentMileage(r.currentMileage());
    }

    private VehicleResponse out(Vehicle v) {
        return new VehicleResponse(v.getId(), v.getVehicleCode(), v.getCustomerId(), v.getRegistrationNumber(), v.getManufacturer(), v.getModel(), v.getManufacturedYear(), v.getFuelType(), v.getTransmissionType(), v.getEngineNumber(), v.getChassisNumber(), v.getCurrentMileage(), v.getVehicleImagePath(), v.isActive(), v.getCreatedAt(), v.getUpdatedAt());
    }
}
