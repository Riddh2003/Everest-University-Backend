package com.everestuniversity.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.service.CourseService;
import com.everestuniversity.service.SemesterService;

@RestController
@RequestMapping("/api/private/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private SemesterService semesterService;

    @GetMapping("/getCourse")
    public ResponseEntity<?> getCourseById(@RequestParam("courseId") String courseId) throws Exception{
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            if(courseService.validateCourse(courseId) == false){
                response.put("message", "Course not found");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }
            CourseEntity course = courseService.getCourseById(courseId);
            response.put("success", true);
            response.put("data", course);

        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listCourses")
    public ResponseEntity<?> listCourses(@RequestParam("semesterId") String semesterId) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            if(semesterService.isSemesterExists(semesterId) == false){
                response.put("message", "Degree not found");
                response.put("success", false);
                return ResponseEntity.badRequest().body(response);
            }
            response.put("success", true);
            response.put("data", courseService.listCourses(semesterId));
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

}
