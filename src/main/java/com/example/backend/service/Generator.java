package com.example.backend.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Generator {
    public static String generate6DigitCode() {
        Random random = new Random();
        int number = random.nextInt(1_000_000); // 0 to 999999
        return String.format("%06d", number);  // pad with leading zeros if needed
    }
}
