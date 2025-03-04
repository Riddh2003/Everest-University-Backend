package com.everestuniversity.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.MaterialDto;
import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.entity.MaterialEntity;
import com.everestuniversity.repository.CourseRepository;
import com.everestuniversity.repository.MaterialRepository;

@Service
public class MaterialService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private MaterialRepository materialRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Validate and retrieve MaterialEntity by ID
    public MaterialEntity validateMaterial(String materialId) {
        String sanitizedId = materialId.startsWith("0x") ? materialId.substring(2) : materialId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return materialRepo.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Material not found with ID: " + materialId));
    }

    // Validate and retrieve CourseEntity by ID
    public CourseEntity validateCourse(String courseId) throws Exception {
        String sanitizedId = courseId.startsWith("0x") ? courseId.substring(2) : courseId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);

        Optional<CourseEntity> course = courseRepo.findById(uuid);
        if (course.isEmpty()) {
            throw new Exception("Course not found");
        }
        return course.get();
    }

    // Save File Logic
    public String saveMaterial(MultipartFile file, String degreeName, Integer semNum, String courseName)
            throws IOException {
        try {
            String fileUrl = cloudinaryService.uploadFileToMaterialsFolder(file, degreeName, semNum, courseName);
            return fileUrl;
        } catch (IOException e) {
            throw new IOException("Failed to save file", e);
        }
    }

    // Map MaterialDto to MaterialEntity Logic
    public MaterialEntity mapDtoToEntity(MaterialDto materialDto, String filePath, CourseEntity course) {
        MaterialEntity materialEntity = new MaterialEntity();
        materialEntity.setTitle(materialDto.getTitle());
        materialEntity.setDescription(materialDto.getDescription());
        materialEntity.setFilePath(filePath);
        materialEntity.setUploadedAt(LocalDateTime.now());
        materialEntity.setCourse(course);
        return materialEntity;
    }

    // Save material logic
    public void saveMaterial(MaterialEntity materialEntity) {
        materialRepo.save(materialEntity);
    }

    // List materials logic
    public List<MaterialEntity> listMaterials(String courseId) {
        String sanitizedId = courseId.startsWith("0x") ? courseId.substring(2) : courseId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);

        List<MaterialEntity> materials = materialRepo.findByCourse_CourseId(uuid);
        return materials;
    }

    // Update material logic
    public MaterialEntity updateMaterial(MaterialEntity existingMaterial,
            MaterialDto materialDto,
            MultipartFile file) throws IOException {
        // if (file != null) {
        // String filePath = saveMaterial(file, courseName);
        // existingMaterial.setFilePath(filePath);
        // }
        if (materialDto.getTitle() != null) {
            existingMaterial.setTitle(materialDto.getTitle());
        }
        if (materialDto.getDescription() != null) {
            existingMaterial.setDescription(materialDto.getDescription());
        }
        return materialRepo.save(existingMaterial);
    }
}
