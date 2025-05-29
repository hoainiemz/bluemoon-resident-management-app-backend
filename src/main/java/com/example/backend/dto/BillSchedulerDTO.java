package com.example.backend.dto;



import com.example.backend.model.Bill;

import java.util.List;

public class BillSchedulerDTO {
    private Integer id;
    private Bill bill;
    private List<Integer> apartmentIds;

    public BillSchedulerDTO(Bill bill, List<Integer> apartmentIds) {
        this.bill = bill;
        this.apartmentIds = apartmentIds;
    }

    public BillSchedulerDTO(Integer id, Bill bill, List<Integer> apartmentIds) {
        this.id = id;
        this.bill = bill;
        this.apartmentIds = apartmentIds;
    }

    public BillSchedulerDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getApartmentIds() {
        return apartmentIds;
    }

    public void setApartmentIds(List<Integer> apartmentIds) {
        this.apartmentIds = apartmentIds;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
