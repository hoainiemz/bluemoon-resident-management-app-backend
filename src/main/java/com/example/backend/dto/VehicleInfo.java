package com.example.backend.dto;

import com.example.backend.model.enums.VehicleType;

import java.time.LocalDateTime;

public class VehicleInfo {
    private Integer vehicleId;
    private String licensePlate;
    private VehicleType vehicleType;
    private LocalDateTime registrationDate;
    private String apartmentName;

    public VehicleInfo(Integer vehicleId, String licensePlate, VehicleType vehicleType, LocalDateTime registrationDate, String apartmentName) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.registrationDate = registrationDate;
        this.apartmentName = apartmentName;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
}
