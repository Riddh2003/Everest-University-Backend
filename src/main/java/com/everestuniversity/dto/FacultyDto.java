package com.everestuniversity.dto;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class FacultyDto {
    UUID facultyId;
    String name;
    String email;
    String password;
    String gender;
    String phoneNumber;
    String address;
    String role;
    String qualification;
    String status = "active";
    String department;
    String profilePicture;
}
