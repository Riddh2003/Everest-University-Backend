package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.entity.AdmisstionEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdmissionRepository;
import com.everestuniversity.repository.AdmissionRequestRepository;
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

    @Autowired
    private MailService mailService;

    @Autowired
    AdmissionRepository admissionRepo;

    @Autowired
    JwtService jwtService;

    // Method to approve registration, transfer data to StudentEntity, and delete
    // from RegistrationEntity
    public void approveAdmission(UUID registrationId) {
    	System.out.println("approve");
        AdmissionRequest request = admissionRequesrRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        String password = PasswordGenerator.generatePassword();
        String encryptedPassword = encoder.encode(password);
        System.out.println(request);
        String jwttoken = jwtService.generateToken(request.getEmail(),"student");
        // Create a new StudentEntity
        StudentEntity student = new StudentEntity();
        student.setEnrollmentId(EnrollmentIdGenerator.generateEnrollmentId(request.getDegree()));
        student.setSurName(request.getSurName());
        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setMobileNo(request.getMobileNo());
        student.setEmail(request.getEmail());
        student.setPassword(encryptedPassword);
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setProgram(request.getProgram());
        student.setDegree(request.getDegree());
        student.setDegreeName(request.getDegreeName());
        student.setCurrentSem(1);
        student.setCurrentYear(1);
        student.setToken(jwttoken);
        student.setCreateAt(LocalDateTime.now());

        studentRepo.save(student);
        System.out.println("student saved");
        mailService.sendEnrollementAndPassword(request.getEmail(), password);
        studentService.setStudentProfile(request.getEmail());
        
    }

    public boolean isEligibleForApproval(UUID admissionId) {
        AdmisstionEntity admission = admissionRepo.findById(admissionId)
                .orElse(null);
        if (!admission.getStatus().equals("APPROVED")) {
            return true; // New admission, eligible for approval
        }
        String status = admission.getStatus();
        return status == null || !status.equalsIgnoreCase("APPROVED");
    }
}
