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
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "analyticsLog")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalyticsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID logId;

    @Column(nullable = false)
    UUID userId;

    @Column(nullable = false)
    String action;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime timestamp;
}
