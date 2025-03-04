package com.everestuniversity.entity;

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

    public UUID getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(UUID degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getDegreeCode() {
        return degreeCode;
    }

    public void setDegreeCode(String degreeCode) {
        this.degreeCode = degreeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
