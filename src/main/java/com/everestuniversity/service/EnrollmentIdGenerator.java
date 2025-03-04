package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentIdGenerator {

    public static String generateEnrollmentId(String programCode) {
        // Validate program code
        if (programCode == null || programCode.isEmpty()) {
            throw new IllegalArgumentException("Program code cannot be null or empty");
        }

        // Get the current timestamp in a short format (e.g., year and sequential value)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String timestamp = LocalDateTime.now().format(formatter);

        // Generate a short random alphanumeric string
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase();

        // Combine program code, timestamp, and random part to create the enrollment ID
        return programCode.toUpperCase() + "-" + timestamp + "-" + randomPart;
    }

    public static void main(String[] args) {
        // Example usage for different programs
        String bcaEnrollmentId = generateEnrollmentId("BCA");
        String bscItEnrollmentId = generateEnrollmentId("BSC.IT");

        System.out.println("Generated BCA Enrollment ID: " + bcaEnrollmentId);
        System.out.println("Generated BSC.IT Enrollment ID: " + bscItEnrollmentId);
    }
}
