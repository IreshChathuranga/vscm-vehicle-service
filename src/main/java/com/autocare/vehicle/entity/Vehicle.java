package com.autocare.vehicle.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles", uniqueConstraints = @UniqueConstraint(name = "uk_vehicle_registration", columnNames = "registrationNumber"))
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String vehicleCode;
    @Column(nullable = false)
    Long customerId;
    @Column(nullable = false)
    String registrationNumber;
    @Column(nullable = false)
    String manufacturer;
    @Column(nullable = false)
    String model;
    Integer manufacturedYear;
    String fuelType;
    String transmissionType;
    String engineNumber;
    String chassisNumber;
    @Column(nullable = false)
    Integer currentMileage = 0;
    String vehicleImagePath;
    boolean active = true;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @PrePersist
    void insert() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void update() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String v) {
        vehicleCode = v;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long v) {
        customerId = v;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String v) {
        registrationNumber = v;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String v) {
        manufacturer = v;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String v) {
        model = v;
    }

    public Integer getManufacturedYear() {
        return manufacturedYear;
    }

    public void setManufacturedYear(Integer v) {
        manufacturedYear = v;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String v) {
        fuelType = v;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String v) {
        transmissionType = v;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String v) {
        engineNumber = v;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String v) {
        chassisNumber = v;
    }

    public Integer getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(Integer v) {
        currentMileage = v;
    }

    public String getVehicleImagePath() {
        return vehicleImagePath;
    }

    public void setVehicleImagePath(String v) {
        vehicleImagePath = v;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean v) {
        active = v;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
