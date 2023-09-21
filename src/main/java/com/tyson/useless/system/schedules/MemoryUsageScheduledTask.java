package com.tyson.useless.system.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MemoryUsageScheduledTask {

    private final ApplicationContext applicationContext;

    @Autowired
    public MemoryUsageScheduledTask(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Scheduled(fixedRate = 3000) // 3 seconds
    public ResponseEntity<String> checkMemoryUsage() {
        System.out.println("Scheduler is running ...");
        if (MemoryUsageChecker.isMemoryUsageFull()) {
            return new ResponseEntity<>("Tyson is sleeping", HttpStatus.BAD_GATEWAY);
        }

        return null;
    }
}
