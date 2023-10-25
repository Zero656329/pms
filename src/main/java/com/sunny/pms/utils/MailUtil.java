package com.sunny.pms.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {
    @Resource
    JavaMailSender javaMailSender;

    //发送带附件的简单邮件
    public void sendAttachFileMail(String to, String cc, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("958098421@qq.com");


        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText(content);

        javaMailSender.send(message);
    }

}
