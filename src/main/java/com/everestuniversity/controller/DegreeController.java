package com.everestuniversity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.entity.DegreeEntity;
import com.everestuniversity.entity.SemesterEntity;
import com.everestuniversity.service.DegreeService;
import com.everestuniversity.service.UUIDService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/private/degree")
public class DegreeController {

    @Autowired
    private DegreeService degreeService;

    // Add Degree API
    @PostMapping("/addDegree")
    public ResponseEntity<?> addDegree(@RequestBody DegreeEntity degree) {

        Map<String, Object> response = new HashMap<>();

        if (degreeService.isDegreeExists(degree.getDegreeName())) {
            response.put("message", "Degree already exists.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Degree added successfully");
        response.put("success", true);
        response.put("data", degreeService.addDegree(degree));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // Get Degree by Id API
    @GetMapping("/getDegree")
    public ResponseEntity<?> getDegreeById(@RequestParam("degreeId") String degreeId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            DegreeEntity degree = degreeService.getDegreeById(degreeId);
            response.put("message", "Degree fetched successfully");
            response.put("success", true);
            response.put("data", degree);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // List Degrees API
    @GetMapping("/listDegrees")
    public ResponseEntity<?> listDegrees() {
        HashMap<String, Object> response = new HashMap<>();
        try {
            response.put("message", "Degrees fetched successfully");
            response.put("success", true);
            response.put("data", degreeService.listDegrees());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Update Degreee API
    @PutMapping("/updateDegree")
    public ResponseEntity<?> updateDegree(@RequestParam String degreeId, @RequestBody DegreeEntity degree) {
        Map<String, Object> response = new HashMap<>();
        try {

            DegreeEntity existingDegree = degreeService.getDegreeById(degreeId);

            DegreeEntity updatedDegree = degreeService.updateDegree(existingDegree, degree);

            response.put("success", true);
            response.put("message", "Degree updated");
            response.put("updatedDegree", updatedDegree);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.ok(response);
        }
    }

    // Add Semester to Degree API
    @PostMapping("/addSemester")
    public ResponseEntity<?> addSemesterToDegree(
            @RequestParam("degreeId") String degreeId,
            @RequestBody SemesterEntity semester) {
        Map<String, Object> response = new HashMap<>();

        String sanitizedId = degreeId.startsWith("0x") ? degreeId.substring(2) : degreeId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);

        // if (degree.getLevel() == "UG") {
        // Long count = semesterRepository.countByDegree_DegreeId(uuid);

        // if (count >= 6) {
        // response.put("success", true);
        // response.put("message", "Cannot add more than 6 semesters for a this
        // degree");
        // return ResponseEntity.ok(response);
        // }
        // } else if (degree.getLevel() == "PG") {
        // Long count = semesterRepository.countByDegree_DegreeId(uuid);

        // if (count >= 4) {
        // response.put("success", true);
        // response.put("message", "Cannot add more than 4 semesters for a this
        // degree");
        // return ResponseEntity.ok(response);
        // }
        // }

        DegreeEntity degree = degreeService.addSemesterToDegree(uuid, semester);

        response.put("message", "Semester added successfully");
        response.put("success", true);
        response.put("data", degree);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
