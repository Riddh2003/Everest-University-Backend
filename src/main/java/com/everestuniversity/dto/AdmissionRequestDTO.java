package com.everestuniversity.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AdmissionRequestDTO {
    String surName;
    String firstName;
    String middleName;
    String mobileNo;
    String email;
    String gender;
    String dateOfBirth;
    String city;
    String state;
    String tenthFilePath;
    String twelthPath;
    String program;
    String degree;
    String degreeName;
}
