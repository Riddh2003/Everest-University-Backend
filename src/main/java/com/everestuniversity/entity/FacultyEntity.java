package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "faculty")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID facultyId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String role;

    @Column(nullable = false)
    String qualification;

    @Column(nullable = false)
    String status;

    @Column(nullable = false)
    String department;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String profilePicture;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    String token;
}
