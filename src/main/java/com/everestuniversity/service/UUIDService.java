package com.everestuniversity.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UUIDService {

  public static UUID formatUuid(String rawUuid) {
    // Ensure the raw UUID is 32 characters
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
