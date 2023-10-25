package com.sunny.pms.utils;

/*import org.springframework.data.redis.core.RedisTemplate;*/
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;

//定时器
@Component
public class ScheduledTask {
/*    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MailUtil mailUtil;*/
    //每5秒扫描redis，发送邮件
    //@Scheduled(cron = "*/5 * * * * *")
/*    public void taskScheduled() throws MessagingException {
        String text=redisTemplate.opsForValue().get("hwj656329@163.com").toString();
        mailUtil.sendAttachFileMail("hwj656329@163.com","","redis",text);
        System.out.println("发送");
    }*/
}
