package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "admin")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID adminId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, unique = true)
    String phoneNumber;

    String qualification;

    @Column(nullable = false)
    String status;

    @Column(nullable = false)
    String role;

    String otp;

    String profilePicture;

    LocalDateTime otpExpirationTime;

    @Column(nullable = true)
    String token;
}
