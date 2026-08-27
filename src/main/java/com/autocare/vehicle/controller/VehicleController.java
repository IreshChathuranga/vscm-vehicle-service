package com.autocare.vehicle.controller;

import com.autocare.vehicle.dto.MileageRequest;
import com.autocare.vehicle.dto.VehicleRequest;
import com.autocare.vehicle.dto.VehicleResponse;
import com.autocare.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService service;

    public VehicleController(VehicleService s) {
        service = s;
    }

    @PostMapping
    VehicleResponse create(@Valid @RequestBody VehicleRequest r) {
        return service.create(r);
    }

    @GetMapping
    List<VehicleResponse> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    VehicleResponse one(@PathVariable Long id) {
        return service.one(id);
    }

    @GetMapping("/registration/{registration}")
    VehicleResponse reg(@PathVariable String registration) {
        return service.registration(registration);
    }

    @GetMapping("/customer/{customerId}")
    List<VehicleResponse> cust(@PathVariable Long customerId) {
        return service.customer(customerId);
    }

    @PutMapping("/{id}")
    VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest r) {
        return service.update(id, r);
    }

    @PatchMapping("/{id}/mileage")
    VehicleResponse mileage(@PathVariable Long id, @Valid @RequestBody MileageRequest r) {
        return service.mileage(id, r);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/image")
    VehicleResponse upload(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        service.upload(id, file);
        return service.one(id);
    }

    @GetMapping("/{id}/image")
    ResponseEntity<ByteArrayResource> image(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(new ByteArrayResource(service.image(id)));
    }
}
