package com.everestuniversity.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class FacultyDto {
    String name;
    String email;
    String password;
    String profilePicture;
    String gender;
    String phoneNumber;
    String address;
    String role;
    String qualification;
    String status;
    String department;
    String token;
}
