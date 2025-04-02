package com.everestuniversity.dto;

import java.util.Date;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDto {
    String name;
    String description;
    Date startDate;
    Date endDate;
    String location;
}
