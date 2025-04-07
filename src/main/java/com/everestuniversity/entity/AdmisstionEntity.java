package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "admissions")
public class AdmisstionEntity {

    @Id
    @GeneratedValue
    UUID admissionId;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String mobileNo;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String degree;

    @Column(nullable = false)
    String status;

    private LocalDateTime createdAt = LocalDateTime.now();

}
