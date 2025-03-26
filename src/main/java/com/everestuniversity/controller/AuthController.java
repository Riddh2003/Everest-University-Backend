package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everestuniversity.dto.LoginRequest;
import com.everestuniversity.entity.AdminEntity;
import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.AdminRepository;
import com.everestuniversity.repository.StudentRepository;
import com.everestuniversity.service.AuthService;
import com.everestuniversity.service.JwtService;
import com.everestuniversity.service.MailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminEntity entity) {
        try {
            // Check if email already exists
            if (adminRepository.findByEmail(entity.getEmail()).isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Email already registered");
                return ResponseEntity.badRequest().body(response);
            }

            // Encode password before saving
            entity.setPassword(encoder.encode(entity.getPassword()));
            adminRepository.save(entity);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Admin registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // User login method
    @PostMapping("/studentlogin")
    public ResponseEntity<?> studentLogin(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<StudentEntity> op = studentRepository.findByEnrollmentId(loginRequest.getEnrollmentId());
            System.out.println("Enrollment ID: " + loginRequest.getEnrollmentId());

            if (op.isEmpty()) {
                response.put("success", false);
                response.put("message", "EnrollmentId is not registered");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            StudentEntity student = op.get();

            if (!encoder.matches(loginRequest.getPassword(), student.getPassword())) {
                response.put("success", false);
                response.put("message", "Incorrect password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String token = jwtService.generateToken(student.getEmail(), "student");

            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("role", "student");

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Admin login method
    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Fetch user from database
            Optional<AdminEntity> op = adminRepository.findByEmail(loginRequest.getEmail());

            if (op.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is not registered");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            AdminEntity adminEntity = op.get();

            // Verify password using BCrypt
            if (!loginRequest.getPassword().equalsIgnoreCase(adminEntity.getPassword())) {
                response.put("success", false);
                response.put("message", "Incorrect password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String token = jwtService.generateToken(adminEntity.getEmail(), "admin");

            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("role", "admin");

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Remove "Bearer " prefix
                jwtService.blacklistToken(token);
                response.put("success", true);
                response.put("message", "Logout successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No token found in request");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Logout failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/sendotp")
    public ResponseEntity<?> sendOtp(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<StudentEntity> op = studentRepository.findByEmail(loginRequest.getEmail());
            if (op.isPresent()) {
                String otp = mailService.sendOtp(loginRequest.getEmail());
                session.setAttribute("otp", otp);
                response.put("success", true);
                response.put("message", "OTP sent successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Email not registered");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String otp = (String) session.getAttribute("otp");
            if (otp == null) {
                response.put("success", false);
                response.put("message", "No OTP found in session. Please request a new OTP.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (authService.checkStudent(loginRequest.getEnrollmentId())) {
                return authService.changePasswordForStudent(loginRequest.getEnrollmentId(), loginRequest.getPassword(),
                        loginRequest.getOtp(), otp);
            } else {
                return authService.changePasswordForAdmin(loginRequest.getEmail(), loginRequest.getPassword(),
                        loginRequest.getOtp(), otp);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Password reset failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
