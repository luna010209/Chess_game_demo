package com.example.game_web.authentication.emailVerification;

import com.example.game_web.authentication.emailVerification.dto.RequestEmail;
import com.example.game_web.authentication.emailVerification.listener.CompleteEvent;
import com.example.game_web.authentication.emailVerification.vertify.VerifiedCode;
import com.example.game_web.authentication.emailVerification.vertify.VerifiedCodeRepo;
import com.example.game_web.exceptionHandler.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerifyService {
    private final ApplicationEventPublisher publisher;
    private final VerifiedCodeRepo codeRepo;
    private final JavaMailSender javaMailSender;

    public String sendCode(RequestEmail request) {
        if (codeRepo.existsByEmailAndIsSuccess(request.getEmail(), true))
            throw new CustomException("Email has already verified", HttpStatus.BAD_REQUEST);
        Optional<VerifiedCode> optional = codeRepo.findByEmail(request.getEmail());

        SecureRandom random = new SecureRandom();
        Integer verifiedCode = 100000 + random.nextInt(900000);

        publisher.publishEvent(new CompleteEvent(request.getEmail(), verifiedCode));

        if (optional.isEmpty()) codeRepo.save(new VerifiedCode(verifiedCode, request.getEmail()));
        else {
            optional.get().setCode(verifiedCode);
            optional.get().setExpirationTime(Date.from(Instant.now().plusSeconds(10*60)));
            codeRepo.save(optional.get());
        }
        return "Please check your email to verify coding!!!";
    }

    public String verifyEmail(RequestEmail requestEmail){
        VerifiedCode emailVerify = codeRepo.findByEmail(requestEmail.getEmail()).orElseThrow(
                ()-> new CustomException("Email does not exist!!!", HttpStatus.NOT_FOUND)
        );
        if (!requestEmail.getCode().equals(emailVerify.getCode()))
            throw new CustomException("Invalid code!!!", HttpStatus.UNAUTHORIZED);
        else if (Date.from(Instant.now()).after(emailVerify.getExpirationTime()))
            throw new CustomException("An expired code!!!", HttpStatus.UNAUTHORIZED);
        emailVerify.setSuccess(true);
        codeRepo.save(emailVerify);
        return "Verify successfully!!!";
    }
}
