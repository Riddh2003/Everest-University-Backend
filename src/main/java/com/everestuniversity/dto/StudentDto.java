package com.everestuniversity.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDto {
	
	String registrationId;
	String surName;
	String firstName;
	String middleName;
	String mobileNo;
	String email;
	String password;
	String gender;
	String dateOfBirth;
	String program;
	String degree;
	String degreeName;
	Integer currentSem;
	Integer currentYear;
	String token;
}
