package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Table(name = "students")
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID studentId;

	@Column(unique = true)
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

	public UUID getStudentId() {
		return studentId;
	}

	public void setStudentId(UUID studentId) {
		this.studentId = studentId;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Integer getCurrentSem() {
		return currentSem;
	}

	public void setCurrentSem(Integer currentSem) {
		this.currentSem = currentSem;
	}

	public Integer getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(Integer curretnYear) {
		this.currentYear = curretnYear;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

}
