package com.everestuniversity.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UUIDService {

  public static UUID formatUuid(String rawUuid) {
    if (rawUuid == null || rawUuid.isEmpty()) {
      throw new IllegalArgumentException("UUID string cannot be null or empty");
    }

    // If already in valid UUID format, return directly
    if (rawUuid.contains("-")) {
      return UUID.fromString(rawUuid);
    }

    // Ensure the raw UUID is exactly 32 characters long
    if (rawUuid.length() == 32) {
      String formattedUuid = rawUuid.replaceFirst(
          "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})",
          "$1-$2-$3-$4-$5");
      
      return UUID.fromString(formattedUuid);
    } else {
      throw new IllegalArgumentException("Invalid UUID string: " + rawUuid);
    }
  }

}
