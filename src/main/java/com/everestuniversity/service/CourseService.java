package com.everestuniversity.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepo;

    public boolean isCourseExists(String courseCode) {
        return courseRepo.findByCourseCode(courseCode).isPresent();
    }

    // Validate and retrieve CourseEntity by ID
    public boolean validateCourse(String courseId) throws Exception {
        String sanitizedId = courseId.startsWith("0x") ? courseId.substring(2) : courseId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);

        Optional<CourseEntity> course = courseRepo.findById(uuid);
        if (course.isEmpty()) {
            // throw new Exception("Course not found");
            return false;
        }
        return true;
    }

    public List<CourseEntity> listCourses(String semesterId) {
        String sanitizedId = semesterId.startsWith("0x") ? semesterId.substring(2) : semesterId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return courseRepo.findBySemester_SemesterId(uuid);
    }

    public CourseEntity getCourseById(String courseId) throws Exception {
        String sanitizedId = courseId.startsWith("0x") ? courseId.substring(2) : courseId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        Optional<CourseEntity> course = courseRepo.findById(uuid);
        if (course.isEmpty()) {
            throw new Exception("Course not found");
        }
        return course.get();
    }
}
