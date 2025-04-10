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
@Table(name = "Semesters")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SemesterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID semesterId;

    @Column(nullable = false)
    int semesterNumber;

    @Column(nullable = false)
    String degreeName;

    @ManyToOne
    @JoinColumn(name = "degreeId", nullable = false)
    DegreeEntity degree;

    @Column(nullable = true)
    @OneToMany(mappedBy = "semester")
    @JsonBackReference
    List<CourseEntity> course;
}
