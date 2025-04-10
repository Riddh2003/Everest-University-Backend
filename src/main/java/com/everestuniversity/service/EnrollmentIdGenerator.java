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

        // Get current year and month
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        String year = LocalDateTime.now().format(formatter);
        
        // Get sequential number padded to 6 digits
        String sequence = String.format("%06d", counter.getAndIncrement());
        
        // Generate department code (2 digits) from program code
        String deptCode = programCode.substring(0, Math.min(programCode.length(), 2)).toUpperCase();
        
        // Combine all parts: YY (2 digits) + DEPT (2 digits) + SEQUENCE (6 digits)
        return year + deptCode + sequence;
    }

    // Reset counter if needed (for testing purposes)
    public static void resetCounter() {
        counter.set(1);
    }
}
