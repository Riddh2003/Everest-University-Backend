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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "studentProfile")
public class StudentProfileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID profileId;

    @OneToOne
    @JoinColumn(name = "enrollmentId", unique = true)
    StudentEntity student;

	@Column(nullable = false)
	String firstname;

	@Column(nullable = false)
	String middlename;

	@Column(nullable = false)
	String surname;

	@Column(nullable = false)
	String fullname;

	@Column(nullable = false, unique = true)
	String email;

	@Column(nullable = false, unique = true)
	String mobileNo;

	@Column(nullable = true)
	String address;

	@Column(nullable = true)
	String dateOfBirth;

	@Column(nullable = false)
	String gender; // Male, Female, Other

	@Column(nullable = true)
	String area;

	@Column(nullable = true)
	String pincode;

	@Column(nullable = true)
	String city;

	@Column(nullable = true)
	String state;

	@Column(nullable = true)
	String nationality;

	@Column(nullable = true)
	String maritalStatus; // Single, Married, Divorced, Widowed

	@Column(nullable = false)
	LocalDateTime createdAt;
}