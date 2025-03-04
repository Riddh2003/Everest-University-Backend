package com.everestuniversity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.everestuniversity.entity.StudentEntity;
import com.everestuniversity.repository.StudentRepository;

@Service
public class MailService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEnrollementAndPassword(String email) {

        Optional<StudentEntity> op = studentRepo.findByEmail(email);
        if (op.isEmpty()) {
            throw new RuntimeException("Student not found");
        }

        StudentEntity student = op.get();
        String fullName = student.getFirstName() + " " + student.getSurName();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplyonthisemail7@gmail.com");
        message.setTo(student.getEmail());
        message.setSubject("🎓 Welcome to [Your Institution Name]! Your Enrollment Details");
        String emailBody = "Hi " + fullName + ",\n\n" +
                "Congratulations on successfully completing your admission to " + student.getDegreeName() +
                " at Everest University!\n\n" +
                "Here are your enrollment details:\n" +
                "- Enrollment ID: " + student.getEnrollmentId() + "\n" +
                "- Password: " + student.getPassword() + "\n\n" +
                "Please keep this information secure, as you will need it to log in to the student portal and access your academic resources.\n\n"
                +
                "Next Steps:\n" +
                "1. Use your Enrollment ID and Password to log in to our Student Portal: [Portal Link].\n" +
                "2. Update your password after your first login for security purposes.\n" +
                "3. Explore the portal to access your course schedule, important announcements, and more.\n\n" +
                "We’re thrilled to have you on board and can’t wait to see all the amazing things you’ll achieve. If you have any questions or face any issues, feel free to contact us at everest@edu.com.\n\n"
                +
                "Welcome to the Everest University! family!\n\n" +
                "Best regards,\n" +
                "Everest University Admissions Team";

        message.setText(emailBody);

        mailSender.send(message);

    }

    public String sendOtp(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        String otp = OtpGenerator.getOtp();
        message.setFrom("noreplyonthisemail7@gmail.com");
        message.setTo(email);
        message.setSubject("OTP for login");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
        return otp;
    }

}
