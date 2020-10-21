package com.sunny.pms.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailUtil {
    @Resource
    JavaMailSender javaMailSender;

    //发送带附件的简单邮件
    public void sendAttachFileMail(String from, String to, String cc, String subject, String content, File file) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setCc(cc);
        helper.setSubject(subject);
        helper.setText(content);
        if (null != file) {
            helper.addAttachment(file.getName(), file);
        }
        javaMailSender.send(message);
    }

}
