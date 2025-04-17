package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "students")
public class StudentEntity {

	@Id
	@Column(unique = true,nullable = false)
	String enrollmentId;

	@Column(nullable = false)
	String surName;

	@Column(nullable = false)
	String firstName;

	@Column(nullable = false)
	String middleName;

	@Column(nullable = false)
	String mobileNo;

	@Column(nullable = false, unique = true)
	String email;

	@Column(unique = true)
	String password;

	@Column(nullable = false)
	String gender;

	@Column(nullable = false)
	String dateOfBirth;

	@Column(nullable = false)
	String program; // e.g., "UG", "PG"

	@Column(nullable = false)
	String degree; // e.g., "BSc", "MSc"

	@Column(nullable = false)
	String degreeName; // e.g., "Bachelor of Science", "Master of Science"

	@Column(nullable = false)
	Integer currentSem; // e.g., 1, 2, 3, 4, 5, 6, 7, 8

	@Column(nullable = false)
	Integer currentYear; // e.g., 1st year, 2nd year, 3rd year, 4th year

	@Column(nullable = false)
	LocalDateTime createAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    String token;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonBackReference
    StudentProfileEntity studentProfile;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    List<FeesEntity> fees;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	List<NotificationEntity> notification;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	List<ResultEntity> result;
}
