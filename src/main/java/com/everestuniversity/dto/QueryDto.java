package com.everestuniversity.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueryDto {
    String title;
    String description;
    String status;
    String category;
}
