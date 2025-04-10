package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    String status;// active, inactive, blocked

    @Column(nullable = false)
    String role;

    @Column(nullable = true)
    String otp;

    String profilePicture;

    @Column(nullable = true)
    LocalDateTime otpExpirationTime;

    @Column(nullable = true)
    String token;

    @Column(nullable = true)
    @OneToMany(mappedBy = "admin")
    List<NotificationEntity> notifications;   
}
