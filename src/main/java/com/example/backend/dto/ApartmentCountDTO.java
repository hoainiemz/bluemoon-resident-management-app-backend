package com.example.backend.dto;

public class ApartmentCountDTO{
    private Integer apartmentId;
    private String apartmentName;
    private Long residentCount;

    // Constructors
    public ApartmentCountDTO() {}

    public ApartmentCountDTO(Integer apartmentId, String apartmentName, Long residentCount) {
        this.apartmentId = apartmentId;
        this.apartmentName = apartmentName;
        this.residentCount = residentCount;
    }


    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }


    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }


    public Long getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(Long residentCount) {
        this.residentCount = residentCount;
    }
}
