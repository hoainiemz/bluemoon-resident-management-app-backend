package com.example.backend.model;

import jakarta.persistence.*;
import com.example.backend.model.enums.VehicleType;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Column(name = "apartment_id", nullable = false)
    private Integer apartmentId;

    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate = LocalDateTime.now();

    // Constructors
    public Vehicle() {}

    public Vehicle(Integer apartmentId, String licensePlate, VehicleType vehicleType) {
        this.apartmentId = apartmentId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.registrationDate = LocalDateTime.now();
    }

    // Getters và Setters

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType  vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
