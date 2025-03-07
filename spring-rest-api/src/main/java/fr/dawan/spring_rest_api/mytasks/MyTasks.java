package fr.dawan.spring_rest_api.mytasks;

import fr.dawan.spring_rest_api.controllers.TestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTasks {

    private static Logger rootLogger = LoggerFactory.getLogger(MyTasks.class);

    /*
    Doc: https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
     */

    @Scheduled(fixedDelay = 3000) //temps en millisecondes
    @Async("myTaskExecutor")
    public void task1(){
        rootLogger.info(">>>> task1........");
        System.out.println(">>>>>>> task1...........");
    }
}
