package com.example.game_web.authentication.emailVerification.listener;

import com.example.game_web.authentication.emailVerification.vertify.VerifiedCode;
import com.example.game_web.authentication.emailVerification.vertify.VerifiedCodeRepo;
import com.example.game_web.exceptionHandler.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompleteEventListener implements ApplicationListener<CompleteEvent> {
    private final VerifiedCodeRepo codeRepo;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(CompleteEvent event) {
        // Create a verification code for email of new user
//        SecureRandom random = new SecureRandom();
//        Integer verifiedCode = 100000 + random.nextInt(900000);
            // In case of generating a string token: c4c31b74-847c-4ea7-a3bb-d2c68e1bdcf2
            // String token = UUID.randomUUID().toString();
        // Save into DB
//        codeRepo.save(new VerifiedCode(event.getCodeVerify(), event.getEmail()));
        log.info(event.getCodeVerify().toString());

        try {
            String subject = "Email Verification";
            String senderName = "Luna Do";
            String mailContent =
                    "<p>Thank you for registering with us.<br>"+
                            "This is the verified code for your registration.</p>"+
                            "<b>"+ event.getCodeVerify() +"</b>"+
                            "<p> Thank you^^ <br><br>"+
                            "Your sincerely, <br> Luna Do";
            MimeMessage message = mailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("liendhhha140217@gmail.com", senderName);
            messageHelper.setTo(event.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e) {
            throw new CustomException("This email does not exist!!!", HttpStatus.BAD_REQUEST);
        }
    }
}
