package com.everestuniversity.dto;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRegistrationDto {
    UUID eventId;
    UUID userId;
}
