package com.everestuniversity.entity;

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
@Data
@Table(name = "Degrees")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DegreeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID degreeId;

    @Column(nullable = false, unique = true)
    private String degreeName; // e.g., "Bachelor of Science"

    @Column(nullable = false, unique = true)
    private String degreeCode; // e.g., "CS01BCA"

    private String description; // e.g., "Bachelor of Science", "Master of Science"

    private String level; // Undergraduate, Postgraduate

    private String duration; // e.g., "3 years"

    @Column(nullable = true)
    @OneToMany(mappedBy = "degree")
    List<SemesterEntity> semester;
}
