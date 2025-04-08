package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "admissionRequest")
public class AdmissionRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID registrationId;
    
    @Column(nullable = false)
    String surName;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String middleName;

    @Column(nullable = false)
    String mobileNo;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String dateOfBirth;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(unique = true)
    String tenthFilePath;

    @Column(unique = true)
    String twelthPath;

    @Column(nullable = false)
    String program; // UG/PG

    @Column(nullable = false)
    String degree; // BCA/MCA

    @Column(nullable = false)
    String degreeName; // Bachelor of Computer Application - full name

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createAt;
}
