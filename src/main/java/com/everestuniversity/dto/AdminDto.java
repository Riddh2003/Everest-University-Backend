package com.everestuniversity.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminDto {
    UUID adminId;
    String name;
    String email;
    String password;
    String phoneNumber;
    String qualification;
    String status;
    MultipartFile profilePicture;
    String role;
    String otp;
    String token;
}
