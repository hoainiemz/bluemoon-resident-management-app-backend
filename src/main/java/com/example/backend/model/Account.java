package com.example.backend.model;

import jakarta.persistence.*;
import com.example.backend.model.enums.AccountType;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String passwordHash;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountType role = AccountType.Resident;

    // No-arg constructor (required by JPA)
    protected Account() {}

    public Account(Integer userId, String username, String email, String phone, String passwordHash, AccountType role) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.phone = phone;
    }

    // Getters (no setters for immutability)
    public Integer getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public AccountType getRole() { return role; }
    public String getPhone() { return phone; }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @PrePersist
    public void setDefaultRole() {
        if (role == null) {
            role = AccountType.Resident;
        }
    }
}
