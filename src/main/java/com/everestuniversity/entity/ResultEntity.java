package com.everestuniversity.entity;

import java.util.UUID;

import jakarta.persistence.Column;
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
@Table(name = "student_results")
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID resultId;

    @Column(nullable = false)
    String studentName;

    @Column(nullable = false)
    String enrollmentId;

    @Column(nullable = false)
    Integer semNumber;

    @Column(nullable = false)
    String subjectName;

    @Column(nullable = false)
    Integer marksObtained;

    @Column(nullable = false)
    Integer totalMarks;

    @Column(nullable = false)
    String grade;

    @Column(nullable = false)
    String resultStatus;

    @Column(nullable = false)
    String degreeName;

    @ManyToOne
    @JoinColumn(name = "studentId")
    StudentEntity student;

    public UUID getResultId() {
        return resultId;
    }

    public void setResultId(UUID resultId) {
        this.resultId = resultId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getSemNumber() {
        return semNumber;
    }

    public void setSemNumber(Integer semNumber) {
        this.semNumber = semNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

}
