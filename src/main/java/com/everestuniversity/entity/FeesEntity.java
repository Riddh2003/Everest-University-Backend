package com.everestuniversity.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "student_fees")
public class FeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID feeId;

    @Column(nullable = false)
    String fee_status;

    Long total_fees;

    Long paid_fees;

    Long due_fees;

    @Column(nullable = false)
    String payment_mode; // e.g., "Online", "Cash", "Cheque", "UPI", etc.

    @OneToOne
    @JoinColumn(name = "studentId")
    StudentEntity student;

}
