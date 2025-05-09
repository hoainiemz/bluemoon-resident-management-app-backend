package com.example.backend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_id")
    private Integer apartmentId;

    @Column(name = "apartment_name", nullable = false, unique = true)
    private String apartmentName;

    @Column(name = "monthly_rent_price", precision = 12, scale = 2)
    private BigDecimal monthlyRentPrice;

    @Column(name = "last_month_electric_index")
    private Integer lastMonthElectricIndex;

    @Column(name = "electric_unit_price", precision = 10, scale = 2)
    private BigDecimal electricUnitPrice;

    @Column(name = "last_month_water_index")
    private Integer lastMonthWaterIndex;

    @Column(name = "water_unit_price", precision = 10, scale = 2)
    private BigDecimal waterUnitPrice;

    public Apartment(Integer apartmentId) {
        this.apartmentId = apartmentId;
        this.apartmentName = null;
        this.monthlyRentPrice = null;
        this.lastMonthElectricIndex = null;
        this.electricUnitPrice = null;
        this.lastMonthWaterIndex = null;
        this.waterUnitPrice = null;
    }
    // Constructors
    public Apartment() {}

    public Apartment(String apartmentName, BigDecimal monthlyRentPrice, Integer lastMonthElectricIndex,
                     BigDecimal electricUnitPrice, Integer lastMonthWaterIndex, BigDecimal waterUnitPrice) {
        this.apartmentName = apartmentName;
        this.monthlyRentPrice = monthlyRentPrice;
        this.lastMonthElectricIndex = lastMonthElectricIndex;
        this.electricUnitPrice = electricUnitPrice;
        this.lastMonthWaterIndex = lastMonthWaterIndex;
        this.waterUnitPrice = waterUnitPrice;
    }

    // Getters and Setters
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

    public BigDecimal getMonthlyRentPrice() {
        return monthlyRentPrice;
    }

    public void setMonthlyRentPrice(BigDecimal monthlyRentPrice) {
        this.monthlyRentPrice = monthlyRentPrice;
    }

    public Integer getLastMonthElectricIndex() {
        return lastMonthElectricIndex;
    }

    public void setLastMonthElectricIndex(Integer lastMonthElectricIndex) {
        this.lastMonthElectricIndex = lastMonthElectricIndex;
    }

    public BigDecimal getElectricUnitPrice() {
        return electricUnitPrice;
    }

    public void setElectricUnitPrice(BigDecimal electricUnitPrice) {
        this.electricUnitPrice = electricUnitPrice;
    }

    public Integer getLastMonthWaterIndex() {
        return lastMonthWaterIndex;
    }

    public void setLastMonthWaterIndex(Integer lastMonthWaterIndex) {
        this.lastMonthWaterIndex = lastMonthWaterIndex;
    }

    public BigDecimal getWaterUnitPrice() {
        return waterUnitPrice;
    }

    public void setWaterUnitPrice(BigDecimal waterUnitPrice) {
        this.waterUnitPrice = waterUnitPrice;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", apartmentName='" + apartmentName + '\'' +
                ", monthlyRentPrice=" + monthlyRentPrice +
                ", lastMonthElectricIndex=" + lastMonthElectricIndex +
                ", electricUnitPrice=" + electricUnitPrice +
                ", lastMonthWaterIndex=" + lastMonthWaterIndex +
                ", waterUnitPrice=" + waterUnitPrice +
                '}';
    }

    public Double getMonthlyFee() {
        return Double.valueOf(getMonthlyRentPrice().doubleValue() + getLastMonthElectricIndex().intValue() * getElectricUnitPrice().doubleValue() + getLastMonthWaterIndex().intValue() * getWaterUnitPrice().doubleValue());
    }
}
