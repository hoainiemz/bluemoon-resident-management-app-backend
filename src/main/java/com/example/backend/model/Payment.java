package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @ManyToOne(fetch = FetchType.LAZY) // lazy loading để hiệu năng tốt hơn
    @JoinColumn(name = "bill_id", referencedColumnName = "bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id", referencedColumnName = "apartment_id")
    private Apartment apartment;

    @Column(name = "pay_amount", precision = 15, scale = 3)
    private BigDecimal payAmount;

    @Column(name = "pay_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime payTime;

    // Constructors
    public Payment() {}

    public Payment(Bill bill, Apartment apartment, BigDecimal payAmount, LocalDateTime payTime) {
        this.bill = bill;
        this.apartment = apartment;
        this.payAmount = payAmount;
        this.payTime = payTime;
    }

    // Getters and Setters
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }
}
