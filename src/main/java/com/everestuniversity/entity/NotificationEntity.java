package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "notifications")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID notification_id;

    @ManyToOne
    @JoinColumn(name = "adminId")
    @JsonBackReference
    AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "studentId")
    @JsonBackReference
    StudentEntity student;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    String notificationType;

    @CreationTimestamp
    LocalDateTime created_at;

}
