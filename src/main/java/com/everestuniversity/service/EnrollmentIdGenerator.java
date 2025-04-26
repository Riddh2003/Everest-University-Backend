package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentIdGenerator {

    private static AtomicInteger counter = new AtomicInteger(1);

    public static String generateEnrollmentId(String programCode) {
        // Validate program code
        if (programCode == null || programCode.isEmpty()) {
            throw new IllegalArgumentException("Program code cannot be null or empty");
        }

        // Get current timestamp for uniqueness
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateCode = LocalDateTime.now().format(formatter);

        // Get sequential number padded to 2 digits
        String sequence = String.format("%02d", counter.getAndIncrement());

        // Generate department code (2 digits) from program code
        String deptCode = programCode.substring(0, Math.min(programCode.length(), 2)).toUpperCase();

        return dateCode + deptCode + sequence;
    }

    public static void resetCounter() {
        counter.set(1);
    }
}
