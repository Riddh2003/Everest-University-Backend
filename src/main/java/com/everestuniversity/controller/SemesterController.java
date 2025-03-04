package com.everestuniversity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.entity.SemesterEntity;
import com.everestuniversity.service.CourseService;
import com.everestuniversity.service.SemesterService;
import com.everestuniversity.service.UUIDService;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/private/semester")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourseToSemester(@RequestParam("semesterId") String semesterId, @RequestBody CourseEntity course) {
        HashMap<String, Object> response = new HashMap<>();
        String sanitizedId = semesterId.startsWith("0x") ? semesterId.substring(2) : semesterId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        if (courseService.isCourseExists(course.getCourseCode())) {
            response.put("message", "Course already exists.");
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
        SemesterEntity semester = semesterService.addCourseToSemester(uuid, course);
        response.put("message", "Course added successfully");
        response.put("success", true);
        response.put("data", semester);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getSemester")
    public ResponseEntity<?> getSemesterById(@RequestParam("semesterId") String semesterId) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            if (semesterService.isSemesterExists(semesterId)) {
                response.put("success",true);
                response.put("data", semesterService.getSemesterById(semesterId));
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
