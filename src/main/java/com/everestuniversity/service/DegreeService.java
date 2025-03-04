package com.everestuniversity.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.DegreeEntity;
import com.everestuniversity.entity.SemesterEntity;
import com.everestuniversity.repository.DegreeRepository;
import com.everestuniversity.repository.SemesterRepository;

@Service
public class DegreeService {

    @Autowired
    private DegreeRepository degreeRepo;

    @Autowired
    private SemesterRepository semesterRepo;

    public boolean isDegreeExists(String degreeName) {
        return degreeRepo.findByDegreeName(degreeName).isPresent();
    }

    public DegreeEntity addDegree(DegreeEntity degree) {
        return degreeRepo.save(degree);
    }

    public DegreeEntity addSemesterToDegree(UUID degreeId, SemesterEntity semester) {
        DegreeEntity degree = degreeRepo.findById(degreeId)
                .orElseThrow(() -> new RuntimeException("Degree not found"));
        semester.setDegree(degree);
        semester.setDegreeName(degree.getDegreeName());
        // degree.getSemesters().add(semester);
        semesterRepo.save(semester);
        return degreeRepo.save(degree);
    }

    public DegreeEntity getDegreeById(String degreeId) {
        String sanitizedId = degreeId.startsWith("0x") ? degreeId.substring(2) : degreeId;
        UUID uuid = UUIDService.formatUuid(sanitizedId);
        return degreeRepo.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Degree not found"));
    }

    public List<DegreeEntity> listDegrees() {
        return degreeRepo.findAll();
    }

    public DegreeEntity updateDegree(DegreeEntity existingDegree, DegreeEntity degree) {
        existingDegree.setDescription(degree.getDescription());
        existingDegree.setDegreeCode(degree.getDegreeCode());
        return degreeRepo.save(existingDegree);
    }

}
