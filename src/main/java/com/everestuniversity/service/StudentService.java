package com.everestuniversity.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.entity.StudentProfileEntity;
import com.everestuniversity.repository.StudentProfileRepository;
import com.everestuniversity.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	StudentProfileRepository studentProfileRepo;

	public boolean validateStudent(String studentId) {
		String sanitizedId = studentId.startsWith("0x") ? studentId.substring(2) : studentId;
		UUID uuid = UUIDService.formatUuid(sanitizedId);
		Optional<StudentEntity> student = studentRepo.findById(uuid);
		if (student.isEmpty()) {
			return false;
		}
		return true;
	}

	public StudentEntity getStudentById(String studentId) {
		String sanitizedId = studentId.startsWith("0x") ? studentId.substring(2) : studentId;
		UUID uuid = UUIDService.formatUuid(sanitizedId);
		Optional<StudentEntity> student = studentRepo.findById(uuid);
		if (student.isEmpty()) {
			return null;
		}
		return student.get();
	}

	public StudentProfileEntity getStudentProfile(String studentProfileId) {
		String sanitizedId = studentProfileId.startsWith("0x") ? studentProfileId.substring(2) : studentProfileId;
		UUID uuid = UUIDService.formatUuid(sanitizedId);

		Optional<StudentProfileEntity> studentProfile = studentProfileRepo.findByStudent_StudentId(uuid);
		if (studentProfile.isEmpty()) {
			return null;
		}
		return studentProfile.get();
	}

	public void setStudentProfile(String email) {

		Optional<StudentEntity> op = studentRepo.findByEmail(email);
		if (op.isPresent()) {
			StudentEntity student = op.get();
			System.out.println("Stduent profile : " + student);
			String fullname = student.getSurName() + " " + student.getFirstName() + " " + student.getMiddleName();
			System.out.println("Fullname : " + fullname);
			StudentProfileEntity studentProfile = new StudentProfileEntity();
			studentProfile.setFirstname(student.getFirstName());
			studentProfile.setMiddlename(student.getMiddleName());
			studentProfile.setSurname(student.getSurName());
			studentProfile.setFullname(fullname);
			studentProfile.setDateOfBirth(student.getDateOfBirth());
			studentProfile.setGender(student.getGender());
			studentProfile.setMobileNo(student.getMobileNo());
			studentProfile.setEmail(student.getEmail());
			studentProfile.setMaritalStatus("N/A");
			studentProfile.setAddress("N/A");
			studentProfile.setArea("N/A");
			studentProfile.setPincode("N/A");
			studentProfile.setState("N/A");
			studentProfile.setCity("N/A");
			studentProfile.setStudent(student);
			studentProfile.setCreateAt(LocalDateTime.now());

			studentProfileRepo.save(studentProfile);
		}
	}

	public StudentProfileEntity updateProfile(StudentProfileEntity existingProfile, StudentProfileEntity newProfile) {

		if (newProfile.getFirstname() != null)
			existingProfile.setFirstname(newProfile.getFirstname());
		if (newProfile.getMiddlename() != null)
			existingProfile.setMiddlename(newProfile.getMiddlename());
		if (newProfile.getSurname() != null)
			existingProfile.setSurname(newProfile.getSurname());
		if (newProfile.getFullname() != null)
			existingProfile.setFullname(newProfile.getFullname());
		if (newProfile.getEmail() != null)
			existingProfile.setEmail(newProfile.getEmail());
		if (newProfile.getMobileNo() != null)
			existingProfile.setMobileNo(newProfile.getMobileNo());
		if (newProfile.getAddress() != null)
			existingProfile.setAddress(newProfile.getAddress());
		if (newProfile.getDateOfBirth() != null)
			existingProfile.setDateOfBirth(null);
		if (newProfile.getGender() != null)
			existingProfile.setGender(newProfile.getGender());
		if (newProfile.getArea() != null)
			existingProfile.setArea(newProfile.getArea());
		if (newProfile.getPincode() != null)
			existingProfile.setPincode(newProfile.getPincode());
		if (newProfile.getCity() != null)
			existingProfile.setCity(newProfile.getCity());
		if (newProfile.getState() != null)
			existingProfile.setState(newProfile.getState());
		if (newProfile.getNationality() != null)
			existingProfile.setNationality(newProfile.getNationality());
		if (newProfile.getMaritalStatus() != null)
			existingProfile.setMaritalStatus(newProfile.getMaritalStatus());

		return existingProfile;

	}

}
