package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "materials")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID materialId;

    @Column(nullable = false, unique = true)
    String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    String description;

    @Column(unique = true)
    String filePath;

    LocalDateTime uploadedAt;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "courseId", nullable = true)
    CourseEntity course;
}
