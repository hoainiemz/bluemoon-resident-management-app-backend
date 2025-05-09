package com.example.backend.dto;

import com.example.backend.model.Account;
import com.example.backend.model.Resident;

public class AccountResidentWrapper {
    private Account profile;
    private Resident resident;

    public Account getProfile() {
        return profile;
    }

    public void setProfile(Account profile) {
        this.profile = profile;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }
}
