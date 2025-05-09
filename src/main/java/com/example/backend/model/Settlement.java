package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "settlement")
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id")
    private Long settlementId;

    @Column(name = "resident_id", nullable = false)
    private Integer residentId;

    @Column(name = "apartment_id", nullable = false)
    private Integer apartmentId;

    // Constructors
    public Settlement() {}

    public Settlement(Integer residentId, Integer apartmentId) {
        this.residentId = residentId;
        this.apartmentId = apartmentId;
    }

    // Getters and Setters
    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public String toString() {
        return "Settlement{" +
                "settlementId=" + settlementId +
                ", residentId=" + residentId +
                ", apartmentId='" + apartmentId + '\'' +
                '}';
    }
}
