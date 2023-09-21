package com.tyson.useless.system.schedules;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class MemoryUsageChecker {

    public static boolean isMemoryUsageFull() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long usedMemory = memoryBean.getHeapMemoryUsage().getUsed();
        long maxMemory = memoryBean.getHeapMemoryUsage().getMax();
        double usedPercentage = ((double) usedMemory / maxMemory) * 100;

//        System.out.println("usedMemory : "+usedMemory);
//        System.out.println("maxMemory : "+maxMemory);
//        System.out.println("usedPercentage : "+usedPercentage);
        return usedPercentage >= 99;
    }
}
