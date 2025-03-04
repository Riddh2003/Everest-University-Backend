package com.everestuniversity.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.AdmissionRequestDTO;
import com.everestuniversity.entity.AdmissionRequest;
import com.everestuniversity.entity.AdmisstionEntity;
import com.everestuniversity.repository.AdmissionRepository;
import com.everestuniversity.repository.AdmissionRequestRepository;

@Service
public class AdmissionRequestService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private AdmissionRequestRepository admissionRequestRepo;

    @Autowired
    private AdmissionRepository admissionRepo;

    public AdmissionRequest mapDtoToEntity(AdmissionRequestDTO admissionRequestDTO, String tenthPath, String twelthPath) {
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
        request.setCreateAt(LocalDateTime.now());
        return request;
    }

    // Save file to cloudinary and return file URL
    public String saveFilePath(MultipartFile file, String fullName) throws IOException {
        try{
            String fileUrl = cloudinaryService.uploadFileToDocumentsFolder(file, fullName);
            return fileUrl;
        } catch (IOException e) {
            throw new IOException("Failed to save file", e);
        }
    }

    public void saveRegistration(AdmissionRequest request) {
        admissionRequestRepo.save(request);
    }

    // Approve registration
    public void approveRegistration(UUID registrationId) {
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        AdmisstionEntity admission = admissionRepo.findByEmail(registration.getEmail());
        admission.setStatus("APPROVED");
        admission.setCreatedAt(LocalDateTime.now());
        admissionRepo.save(admission);
    }

    // Reject registration
    public void rejectRegistration(UUID registrationId) {
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        AdmisstionEntity admission = admissionRepo.findByEmail(registration.getEmail());
        admission.setStatus("REJECTED");
        admission.setCreatedAt(LocalDateTime.now());

        admissionRepo.save(admission);
    }

    // Save registration to admission table
    public void saveAdmission(UUID registrationId){
        AdmissionRequest registration = admissionRequestRepo.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        AdmisstionEntity admission = new AdmisstionEntity();
        admission.setFullName(registration.getSurName() + " " + registration.getFirstName() + " " + registration.getMiddleName());
        admission.setEmail(registration.getEmail());
        admission.setMobileNo(registration.getMobileNo());
        admission.setGender(registration.getGender());
        admission.setDegree(registration.getDegree());
        admission.setStatus("PENDING");
        admission.setCreatedAt(LocalDateTime.now());

        admissionRepo.save(admission);
    }

}
