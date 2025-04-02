package com.everestuniversity.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialDto {

    String title;
    String description;
    MultipartFile file;
}
