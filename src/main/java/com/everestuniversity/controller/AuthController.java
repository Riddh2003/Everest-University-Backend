package com.everestuniversity.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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
@RequestMapping("/api/public/auth/")
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
        entity.setPassword(encoder.encode(entity.getPassword()));
        adminRepository.save(entity);
        return ResponseEntity.ok("Admin registered successfully.");
    }

    // User login method
    @PostMapping("/studentlogin")
    public ResponseEntity<?> studentLogin(@RequestBody LoginRequest loginRequest) {

        Map<String, Object> response = new HashMap<>();

        Optional<StudentEntity> op = studentRepository.findByEnrollmentId(loginRequest.getEnrollmentId());
        System.out.println("Enrollment ID: " + loginRequest.getEnrollmentId());
        System.out.println("Password: " + loginRequest.getPassword());
        if (op.isEmpty()) {
            response.put("success", false);
            response.put("message", "EnrollmentId is not registered");
            return ResponseEntity.ok(response);
        }

        StudentEntity student = op.get();

        if (student.getEnrollmentId().equals(loginRequest.getEnrollmentId())) {
            if (!encoder.matches(loginRequest.getPassword(), student.getPassword())) {
                response.put("success", false);
                response.put("message", "Incorrect password");
                return ResponseEntity.ok(response);
            }
        } else {
            response.put("success", false);
            response.put("message", "Incorrect enrollmentId");
            return ResponseEntity.ok(response);
        }

        String token = jwtService.generateToken(student.getEmail(), "student");

        response.put("success", true);
        response.put("message", "Login succesfully");
        response.put("token", token);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(response);
    }

    // Admin login method
    @PostMapping("/adminlogin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest loginRequest) {

        Map<String, Object> response = new HashMap<>();
        // Map<String, Object> requestBody = new HashMap<>();

        // Fetch user from database
        Optional<AdminEntity> op = adminRepository.findByEmail(loginRequest.getEmail());

        if (op.isEmpty()) {
            response.put("success", false);
            response.put("message", "Email is not registered");
            return ResponseEntity.badRequest().body(response);
        }
        AdminEntity adminEntity = op.get();
        if (adminEntity.getEmail().equals(loginRequest.getEmail())) {

            // Verify password
            if (!loginRequest.getPassword().equalsIgnoreCase(adminEntity.getPassword())) {
                response.put("success", false);
                response.put("message", "Wrong password.");
                return ResponseEntity.ok(response);
            }
        } else {
            response.put("success", false);
            response.put("message", "Wrong email");
            return ResponseEntity.ok(response);
        }

        String token = jwtService.generateToken(adminEntity.getEmail(), "admin");

        response.put("success", true);
        response.put("message", "Login succesfully");
        response.put("token", token);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(response);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> studentLogout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            jwtService.blacklistToken(token);
            return ResponseEntity.ok("Logout successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token found in request");
        }
    }

    @PostMapping("/sendotp")
    public ResponseEntity<?> sendOtp(@RequestBody LoginRequest loginRequest, HttpSession session) {
        HashMap<String, Object> response = new HashMap<>();
        Optional<StudentEntity> op = studentRepository.findByEmail(loginRequest.getEmail());
        if (op.isPresent()) {
            response.put("success", true);
            response.put("message", "Otp sent successfully");
            session.setAttribute("otp", mailService.sendOtp(loginRequest.getEmail()));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Email not registered");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest, HttpSession session) {

        String otp = (String) session.getAttribute("otp");

        if (authService.checkStudent(loginRequest.getEnrollmentId()) != false) {
            return authService.changePasswordForStudent(loginRequest.getEnrollmentId(), loginRequest.getPassword(),
                    loginRequest.getOtp(), otp);
        } else {
            return authService.changePasswordForAdmin(loginRequest.getEmail(), loginRequest.getPassword(),
                    loginRequest.getOtp(), otp);
        }

    }

}
