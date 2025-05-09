package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentProjectionDTO{
    private Integer paymentId;
    private Integer billId;
    private LocalDateTime dueDate;
    private Boolean required;
    private String content;
    private Double amount;
    private LocalDateTime payTime;
    private String apartmentName;

    public PaymentProjectionDTO() {}

    public PaymentProjectionDTO(Integer paymentId, Integer billId, LocalDateTime dueDate, Boolean required, String content, Double amount, LocalDateTime payTime, String apartmentName) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.dueDate = dueDate;
        this.required = required;
        this.content = content;
        this.amount = amount;
        this.payTime = payTime;
        this.apartmentName = apartmentName;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }
}
