package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "eventRegistration")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID registrationId;

    @ManyToOne
    @JoinColumn(name = "eventId")
    @JsonBackReference
    EventEntity event;

    @CreationTimestamp
    LocalDateTime registrationDate;
}
