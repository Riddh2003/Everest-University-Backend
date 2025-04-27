package com.everestuniversity.entity;

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
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "student_results")
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID resultId;

    @Column(nullable = false)
    String studentName;

    @Column(nullable = false)
    Integer semNumber;

    @Column(nullable = false)
    String subjectName;

    @Column(nullable = false)
    Integer marksObtained;

    @Column(nullable = false)
    Integer totalMarks;

    @Column(nullable = false)
    String grade;

    @Column(nullable = false)
    String resultStatus;

    @Column(nullable = false)
    String degreeName;

    @ManyToOne
    @JoinColumn(name = "enrollmentId")
    @JsonBackReference
    StudentEntity student;
}
