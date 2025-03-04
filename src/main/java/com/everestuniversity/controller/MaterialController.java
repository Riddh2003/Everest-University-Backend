package com.everestuniversity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.everestuniversity.dto.MaterialDto;
import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.entity.MaterialEntity;
import com.everestuniversity.service.MaterialService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/private/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // Add Material API
    @PostMapping("/addMaterial")
    public ResponseEntity<?> addMaterial(
            @RequestParam String courseId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("material") String materialJson) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MaterialDto materialDto = objectMapper.readValue(materialJson, MaterialDto.class);

            CourseEntity course = materialService.validateCourse(courseId);
            String filePath = materialService.saveMaterial(file, course.getDegreeName(),
                    course.getSemester().getSemesterNumber(), course.getCourseName());

            MaterialEntity materialEntity = materialService.mapDtoToEntity(materialDto, filePath, course);
            materialService.saveMaterial(materialEntity);
            response.put("message", "File uploaded successfully");
            response.put("filePath", filePath);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

    // List Materials API
    @GetMapping("/listMaterials")
    public ResponseEntity<?> listMaterials(@RequestParam String courseId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            CourseEntity course = materialService.validateCourse(courseId);
            List<MaterialEntity> materials = materialService.listMaterials(courseId);
            response.put("message", "Materials retrieved successfully");
            response.put("success", true);
            response.put("data", materials);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

    // Update Material API
    @PutMapping("/updateMaterial")
    public ResponseEntity<?> updateMaterial(
            @RequestParam String materialId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "material", required = false) String materialJson) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            // Validate material
            MaterialEntity existingMaterial = materialService.validateMaterial(materialId);

            // Parse material JSON if provided
            MaterialDto materialDto = null;
            if (materialJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                materialDto = objectMapper.readValue(materialJson, MaterialDto.class);
            }

            // Update material
            MaterialEntity updatedMaterial = materialService.updateMaterial(existingMaterial, materialDto, file);

            response.put("message", "Material updated successfully");
            response.put("success", true);
            response.put("updatedMaterial", updatedMaterial); // Optional: include the updated entity
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.status(500).body(response);
        }
    }

}
