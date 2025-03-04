package com.everestuniversity.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "studentDocuments")
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID documentId;

    String documentTitle; // e.g., 10th Marksheet, 12th Marksheet, Aadhar Card, etc.

    String documentType; // e.g., Image, PDF, etc.

    String documentPath; // e.g., /home/user/documents/10thMarksheet.pdf

    LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "studentId")
    StudentEntity student;

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

}
