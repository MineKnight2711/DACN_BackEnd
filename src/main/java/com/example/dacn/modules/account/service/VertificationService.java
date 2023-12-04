package com.example.dacn.modules.account.service;

import com.example.dacn.entity.ResponseModel;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class VertificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FirebaseAuth firebaseAuth;
    public ResponseModel createEmailVerificationLink(String email,String password)
    {

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setEmailVerified(false);
            UserRecord user=firebaseAuth.createUser(request);
            ActionCodeSettings actionCodeSettings = ActionCodeSettings.builder()
                    .setUrl("http://localhost:6969/verifyEmail")
                    .setDynamicLinkDomain("FoodDeliveryV2.page.link")
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName("com.example.fooddelivery_fe")
                    .build();
            String verificationLink=firebaseAuth.generateEmailVerificationLink(user.getEmail(), actionCodeSettings);
            return new ResponseModel("Succes",verificationLink) ;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new ResponseModel("Fail                                                     cxx",e.getMessage());
        }

    }
    // Method to send OTP to user's email
    public ResponseModel sendVerificationLinkToEmail(String activationLink, String email)   {
        try {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCharacterEncoding("UTF-8");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            Context context = new Context();
            context.setVariable("name", email);
            context.setVariable("activationLink", activationLink);
            context.setVariable("appName", "Dat Food Delivery");

            String processedHtml = templateEngine.process("templates/vertification", context);


            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("witherdragonlair@gmail.com");
            helper.setTo(email);
            helper.setSubject("Xác thực tài khoản");
            helper.setText(processedHtml, true);
            javaMailSender.send(message);
            return new ResponseModel("Success",activationLink);
        }catch (MessagingException e)
        {
            e.printStackTrace();
            return new ResponseModel("Fail","Không thể gửi email");
        }
    }
}