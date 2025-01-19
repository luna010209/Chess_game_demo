package com.example.game_web.authentication.event;

import com.example.game_web.authentication.event.dto.CompleteEvent;
import com.example.game_web.authentication.event.dto.RequestEmail;
import com.example.game_web.authentication.event.vertify.VerifiedCode;
import com.example.game_web.authentication.event.vertify.VerifiedCodeRepo;
import com.example.game_web.exceptionHandler.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class VerifyService {
    private final ApplicationEventPublisher publisher;
    private final VerifiedCodeRepo codeRepo;
    private final JavaMailSender javaMailSender;

    public VerifiedCode verifyEmail(RequestEmail request) {
        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000 + random.nextInt(900000);

        try {
            String subject = "Email Verification";
            String senderName = "Luna Do";
            String mailContent =
                    "<p>Thank you for registering with us,"+"" +
                    "This is the verified code for your registration.</p>"+
                    "<b>"+ verifiedCode +"</b>"+
                    "<p> Thank you <br> Users Registration Portal Service";
            MimeMessage message = javaMailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("liendhhha140217@gmail.com", senderName);
            messageHelper.setTo(request.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e) {
            throw new CustomException("This email does not exist!!!", HttpStatus.BAD_REQUEST);
        }
        return codeRepo.save(new VerifiedCode(verifiedCode, request.getEmail()));
    }
}
