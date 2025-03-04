package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdmissionRequestRepository;
import com.everestuniversity.repository.StudentProfileRepository;
import com.everestuniversity.repository.StudentRepository;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRequestRepository admissionRequesrRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private StudentService studentService;

    // Method to approve registration, transfer data to StudentEntity, and delete
    // from RegistrationEntity
    public void approveAdmission(UUID registrationId) {
        AdmissionRequest request = admissionRequesrRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        String encryptedPassword = encoder.encode(PasswordGenerator.generatePassword());
        // Create a new StudentEntity
        StudentEntity student = new StudentEntity();
        student.setSurName(request.getSurName());
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setMobileNo(request.getMobileNo());
        student.setEmail(request.getEmail());
        student.setPassword(encryptedPassword);
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setEnrollmentId(EnrollmentIdGenerator.generateEnrollmentId(request.getDegree()));
        student.setProgram(request.getProgram());
        student.setDegree(request.getDegree());
        student.setDegreeName(request.getDegreeName());
        student.setCurrentSem(1);
        student.setCurrentYear(1);
        student.setCreateAt(LocalDateTime.now());

        studentRepo.save(student);
        
        studentService.setStudentProfile(request.getEmail());
        
    }

}
