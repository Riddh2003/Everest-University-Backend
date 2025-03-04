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

@Entity
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

    public UUID getFeeId() {
        return feeId;
    }

    public void setFeeId(UUID feeId) {
        this.feeId = feeId;
    }

    public String getFee_status() {
        return fee_status;
    }

    public void setFee_status(String fee_status) {
        this.fee_status = fee_status;
    }

    public Long getTotal_fees() {
        return total_fees;
    }

    public void setTotal_fees(Long total_fees) {
        this.total_fees = total_fees;
    }

    public Long getPaid_fees() {
        return paid_fees;
    }

    public void setPaid_fees(Long paid_fees) {
        this.paid_fees = paid_fees;
    }

    public Long getDue_fees() {
        return due_fees;
    }

    public void setDue_fees(Long due_fees) {
        this.due_fees = due_fees;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

}
