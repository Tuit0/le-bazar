package uz.pdp.lebazar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.lebazar.entity.User;
import uz.pdp.lebazar.payload.ApiResponse;
import uz.pdp.lebazar.repository.UserRepository;

@Service
public class MailService {
    @Autowired
    JavaMailSender sender;

    @Autowired
    UserRepository userRepository;

    public ApiResponse sendText(String email, String generation) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setText("This code is for you that registered my test site: " + generation);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Verification Test:");
            sender.send(simpleMailMessage);
            return new ApiResponse("Send", true,null,null,generation);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error", false);
        }
    }
}
