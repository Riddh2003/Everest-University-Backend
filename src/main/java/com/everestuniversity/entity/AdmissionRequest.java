package com.everestuniversity.entity;

import java.time.LocalDate;
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
@Table(name = "admissionRequest")
public class AdmissionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID registrationId;

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

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String dateOfBirth;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(unique = true)
    String tenthFilePath;

    @Column(unique = true)
    String twelthPath;

    @Column(nullable = false)
    String program; // UG/PG

    @Column(nullable = false)
    String degree; // BCA/MCA

    @Column(nullable = false)
    String degreeName; // Bachelor of Computer Application - full name

    LocalDateTime createAt;

    public UUID getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(UUID registrationId) {
        this.registrationId = registrationId;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenthFilePath() {
        return tenthFilePath;
    }

    public void setTenthFilePath(String tenthFilePath) {
        this.tenthFilePath = tenthFilePath;
    }

    public String getTwelthPath() {
        return twelthPath;
    }

    public void setTwelthPath(String twelthPath) {
        this.twelthPath = twelthPath;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

}
