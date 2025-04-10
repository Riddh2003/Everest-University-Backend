package com.everestuniversity.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationDto {
    String enrollmentId;
    String message;
    String notificationType;
}
