package com.tyson.useless.system.schedules;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class RestartApplication {

    public static void restart(ApplicationContext context, String errorMessage) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println(errorMessage);
                Thread.sleep(1000); // Give some time for the message to be printed

            } catch (InterruptedException ignored) {}

            ((ConfigurableApplicationContext) context).close();
            System.exit(0);
        });

        thread.setDaemon(false);
        thread.start();
    }
}
