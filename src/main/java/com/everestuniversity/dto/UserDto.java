package com.everestuniversity.dto;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    UUID userId;
    String name;
    String email;
    String password;
    String phoneNumber;
    String role;
    String status;
}
