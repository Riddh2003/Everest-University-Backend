package com.everestuniversity.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.CourseEntity;
import com.everestuniversity.entity.SemesterEntity;
import com.everestuniversity.repository.CourseRepository;
import com.everestuniversity.repository.SemesterRepository;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepo;

    @Autowired
    private CourseRepository courseRepo;

    public SemesterEntity addCourseToSemester(UUID semesterId, CourseEntity course) {
        SemesterEntity semester = semesterRepo.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        System.out.println("Degree name" + semester.getDegree().getDegreeName());
        course.setSemester(semester);
        course.setDegreeName(semester.getDegree().getDegreeName());
        courseRepo.save(course);

        return semesterRepo.save(semester);
    }

    public boolean isSemesterExists(String semesterId) {
        String sanitizedId = semesterId.startsWith("0x") ? semesterId.substring(2) : semesterId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return semesterRepo.existsById(uuid);
    }

    public List<SemesterEntity> listSemesters(String degreeId) {
        String sanitizedId = degreeId.startsWith("0x") ? degreeId.substring(2) : degreeId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return semesterRepo.findByDegree_DegreeId(uuid);
    }

    public SemesterEntity getSemesterById(String semesterId) throws Exception {
        String sanitizedId = semesterId.startsWith("0x") ? semesterId.substring(2) : semesterId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return semesterRepo.findById(uuid)
                .orElseThrow(() -> new Exception("Semester not found"));
    }

}
