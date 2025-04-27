package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class EnrollmentIdGenerator {

    private static AtomicInteger counter = new AtomicInteger(1);

    public static String generateEnrollmentId() {
        StringBuilder idBuilder = new StringBuilder();

        // Get current timestamp for uniqueness
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String dateCode = LocalDateTime.now().format(formatter);
        idBuilder.append(dateCode); // First 6 digits from date

        // Get sequential number for uniqueness
        int sequentialNumber = counter.getAndIncrement();

        // Ensure the ID is exactly 10 digits long
        int remainingDigits = 10 - dateCode.length();
        String sequenceFormatted = String.format("%0" + remainingDigits + "d", sequentialNumber);
        idBuilder.append(sequenceFormatted);

        // Ensure the ID is exactly 10 digits
        String enrollmentId = idBuilder.toString();
        if (enrollmentId.length() > 10) {
            enrollmentId = enrollmentId.substring(0, 10);
        }

        return enrollmentId;
    }

    public static void resetCounter() {
        counter.set(1);
    }
}
