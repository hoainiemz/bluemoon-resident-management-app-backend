package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.example.backend.model.enums.GenderType;

import java.time.LocalDate;

@Entity
@Table(name = "resident")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private Integer residentId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "identity_card", length = 20, unique = true)
    private String identityCard;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "move_in_date")
    private LocalDate moveInDate;

    // Constructors
    public Resident() {
        this.userId = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.dateOfBirth = null;
        this.identityCard = null;
        this.moveInDate = LocalDate.now();
    }

    public Resident(Integer userId, String firstName, String lastName, GenderType gender, LocalDate dateOfBirth,
                    String identityCard, LocalDate moveInDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.identityCard = identityCard;
        this.moveInDate = moveInDate;
    }

    public Resident(Integer residentId, Integer userId, String firstName, String lastName, GenderType gender, LocalDate dateOfBirth,
                    String identityCard, LocalDate moveInDate) {
        this.residentId = residentId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.identityCard = identityCard;
        this.moveInDate = moveInDate;
    }

    // Getters and Setters
    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }
}
