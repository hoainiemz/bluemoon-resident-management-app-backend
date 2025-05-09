package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer billId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "late_fee")
    private Double lateFee;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private String description;

    @Column(name = "required")
    private Boolean required;

    public Bill(Integer billId, Double amount, Double lateFee, LocalDateTime dueDate, String content, String description, Boolean required) {
        this.billId = billId;
        this.amount = amount;
        this.lateFee = lateFee;
        this.dueDate = dueDate;
        this.content = content;
        this.description = description;
        this.required = required;
    }

    public Bill() {
    }

    public Bill(Integer billId) {
        this.billId = billId;
        this.amount = null;
        this.lateFee = null;
        this.dueDate = null;
        this.content = null;
        this.description = null;
        this.required = null;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Double getFee() {
        return amount;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Bill bill = (Bill) obj;

        return (billId != null && billId.equals(bill.billId)) &&
                (amount != null && amount.equals(bill.amount)) &&
                (lateFee != null && lateFee.equals(bill.lateFee)) &&
                (dueDate != null && dueDate.equals(bill.dueDate)) &&
                (content != null && content.equals(bill.content)) &&
                (description != null && description.equals(bill.description)) &&
                (required != null && required.equals(bill.required));
    }

}
