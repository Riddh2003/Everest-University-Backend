package com.everestuniversity.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import com.everestuniversity.dto.AdmissionRequestDTO;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.entity.AdmisstionEntity;
import com.everestuniversity.repository.AdmissionRepository;
import com.everestuniversity.repository.AdmissionRequestRepository;
import com.everestuniversity.repository.StudentRepository;

@Service
@Transactional
public class AdmissionRequestService {

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    AdmissionRequestRepository admissionRequestRepo;

    @Autowired
    AdmissionRepository admissionRepo;
    
    @Autowired
    StudentRepository studentRepository;

    public AdmissionRequest mapDtoToEntity(AdmissionRequestDTO admissionRequestDTO, String tenthPath,
            String twelthPath) {
        AdmissionRequest request = new AdmissionRequest();
        request.setSurName(admissionRequestDTO.getSurName());
        request.setFirstName(admissionRequestDTO.getFirstName());
        request.setMiddleName(admissionRequestDTO.getMiddleName());
        request.setEmail(admissionRequestDTO.getEmail());
        request.setMobileNo(admissionRequestDTO.getMobileNo());
        request.setGender(admissionRequestDTO.getGender());
        request.setDateOfBirth(admissionRequestDTO.getDateOfBirth());
        request.setTenthFilePath(tenthPath);
        request.setTwelthPath(twelthPath);
        request.setCity(admissionRequestDTO.getCity());
        request.setState(admissionRequestDTO.getState());
        request.setProgram(admissionRequestDTO.getProgram());
        request.setDegree(admissionRequestDTO.getDegree());
        request.setDegreeName(admissionRequestDTO.getDegreeName());
        request.setCreatedAt(LocalDateTime.now());
        return request;
    }

    // Save file to cloudinary and return file URL
    public String saveFilePath(MultipartFile file, String fullName) throws IOException {
        try {
            String fileUrl = cloudinaryService.uploadFileToDocumentsFolder(file, fullName);
            return fileUrl;
        } catch (IOException e) {
            throw new IOException("Failed to save file", e);
        }
    }

    public boolean existsByEmail(String email) {
        return admissionRequestRepo.findByEmail(email).isPresent();
    }

    @Transactional
    public void saveRegistration(AdmissionRequest request) {
        try {
            admissionRequestRepo.save(request);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already registered", e);
        }
    }

    // Approve registration
    public void approveRegistration(UUID registrationId) {
        System.out.println("Registration id :"+registrationId);
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        System.out.println("Registration :"+registration);
        try {
            Optional<AdmisstionEntity> op = admissionRepo.findByEmail(registration.getEmail());
            if(op.isEmpty()){
                AdmisstionEntity admission = new AdmisstionEntity();
                admission = new AdmisstionEntity();
                admission = new AdmisstionEntity();
                // Set all required fields from registration to admission
                admission.setFullName(
                        registration.getSurName() + " " + registration.getFirstName() + " " + registration.getMiddleName());
                admission.setEmail(registration.getEmail());
                admission.setMobileNo(registration.getMobileNo());
                admission.setGender(registration.getGender());
                admission.setDegree(registration.getDegree() + " " + registration.getDegreeName());
                admission.setStatus("APPROVED");
                admission.setCreatedAt(LocalDateTime.now());
                
                // Save the complete admission entity
                admissionRepo.save(admission);
                System.out.println("admission saved");
            }
            else{
                AdmisstionEntity admission = op.get();
                if (admission.getStatus().equals("PENDING")) {
                    admission.setStatus("APPROVED");
                    admissionRepo.save(admission);
                    System.out.println("admission saved");
                }
                else if(admission.getStatus().equals("APPROVED")){
                    System.out.println("admission already approved");
                }
                else if(admission.getStatus().equals("REJECTED")){
                    System.out.println("admission already rejected");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing admission approval", e);
        }
        
    }

    // Reject registration
    public void rejectRegistration(UUID registrationId) {
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        Optional<AdmisstionEntity> op = admissionRepo.findByEmail(registration.getEmail());
        AdmisstionEntity admission = op.get();
        admission.setStatus("REJECTED");
        admission.setCreatedAt(LocalDateTime.now());
        admissionRequestRepo.delete(registration);

        admissionRepo.save(admission);
    }

    // Save registration to admission table
    public void saveAdmission(UUID registrationId) {
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        AdmisstionEntity admission = new AdmisstionEntity();
        admission.setFullName(
                registration.getSurName() + " " + registration.getFirstName() + " " + registration.getMiddleName());
        admission.setEmail(registration.getEmail());
        admission.setMobileNo(registration.getMobileNo());
        admission.setGender(registration.getGender());
        admission.setDegree(registration.getDegree());
        admission.setStatus("PENDING");
        admission.setCreatedAt(LocalDateTime.now());

        admissionRepo.save(admission);
    }

}
