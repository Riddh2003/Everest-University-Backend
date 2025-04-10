package com.everestuniversity.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "courses")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID courseId;

    @Column(nullable = false, unique = true)
    String courseName;

    @Column(nullable = false, unique = true)
    String courseCode;

    String description;

    int credits;

    @Column(nullable = false)
    String degreeName;

    @ManyToOne
    @JoinColumn(name = "semesterId", nullable = false)
    @JsonBackReference
    SemesterEntity semester;

    @OneToMany(mappedBy = "course")
    List<MaterialEntity> material;

}
