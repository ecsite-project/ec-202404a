package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

/**
 * メール送信を処理するサービス.
 *
 * @author rui.inoue
 */
@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromAddress;

    /**
     * メール送信.
     *
     * @param toAddress 送信先アドレス
     * @param variables htmlの変数
     * @throws MessagingException メール関連の例外処理
     */
    public void mailSender(String toAddress, Map<String, Object> variables) throws MessagingException {
        MimeMessage  message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        messageHelper.setFrom(fromAddress);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject("注文完了のお知らせ");

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process("mail/order-finished", context);

        messageHelper.setText(htmlContent, true);

        javaMailSender.send(message);
    }
}
