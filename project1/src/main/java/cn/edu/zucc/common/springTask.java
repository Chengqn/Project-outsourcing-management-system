package cn.edu.zucc.common;


import cn.edu.zucc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class springTask {

    @Autowired
    UserService userService;

    @Async
    @Scheduled(cron = "0/1 * * * * *")   //Seconds Minutes Hours DayofMonth Month DayofWeek *表示任意值 从0开始每间隔一秒
    public void scheduled1() throws  InterruptedException{
        System.out.println(LocalDateTime.now()+"\tTom的邮箱是"+userService.getUser("Tom").getEmail());
    }



}
