package com.everestuniversity.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.StudentRepository;

@Service
public class AuthService {

	@Autowired
	StudentRepository studentRepo;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	
	public boolean checkStudent(String enrollmentId) {
		Optional<StudentEntity> studentOp = studentRepo.findByEnrollmentId(enrollmentId);
		if(studentOp.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean checkAdmin(String email) {
		Optional<AdminEntity> adminOp = adminRepo.findByEmail(email);
		if(adminOp.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public ResponseEntity<?> changePasswordForStudent(String enrollmentId, String password, String newOtp, String  oldOtp) {
		
		HashMap<String, Object> response = new HashMap<>();
		
		Optional<StudentEntity> op = studentRepo.findByEnrollmentId(enrollmentId);
	    
	      if (op.isPresent()) {
	    	  
	          StudentEntity student = op.get();
	          System.out.println("Enrollment id: " + student.getEnrollmentId());
	          System.out.println("Enrollment id: " + enrollmentId);
	          
	          if (student.getEnrollmentId().equals(enrollmentId)) {
	        	  
	              if (newOtp.equals(oldOtp)) {
	                  student.setPassword(encoder.encode(password));
	                  studentRepo.save(student);
	                  response.put("success", true);
	                  response.put("message", "Password changed successfully");
	                  return ResponseEntity.ok(response);
	                  
	              } else {
	                  response.put("success", false);
	                  response.put("message", "Wrong otp");
	                  return ResponseEntity.ok(response);
	              }
	          } else {
	              response.put("success", false);
	              response.put("message", "Wrong enrollment id");
	              return ResponseEntity.ok(response);
	          }
	      } else {
	          response.put("success", false);
	          response.put("message", "Wrong email");
	          return ResponseEntity.ok(response);
	      }
      
	}
	
	public ResponseEntity<?> changePasswordForAdmin(String email, String password, String newOtp, String  oldOtp) {
		
		HashMap<String, Object> response = new HashMap<>();
		
		Optional<AdminEntity> op = adminRepo.findByEmail(email);
	    
	      if (op.isPresent()) {
	    	  
	    	  AdminEntity admin = op.get();
	          System.out.println("Enrollment id: " + admin.getEmail());
	          System.out.println("Enrollment id: " + email);
	          
	          if (admin.getEmail().equals(email)) {
	        	  
	              if (newOtp.equals(oldOtp)) {
	            	  admin.setPassword(password);
	                  adminRepo.save(admin);
	                  response.put("success", true);
	                  response.put("message", "Password changed successfully");
	                  return ResponseEntity.ok(response);
	                  
	              } else {
	                  response.put("success", false);
	                  response.put("message", "Wrong otp");
	                  return ResponseEntity.ok(response);
	              }
	          } else {
	              response.put("success", false);
	              response.put("message", "Wrong enrollment id");
	              return ResponseEntity.ok(response);
	          }
	      } else {
	          response.put("success", false);
	          response.put("message", "Wrong email");
	          return ResponseEntity.ok(response);
	      }
      
	}

}
