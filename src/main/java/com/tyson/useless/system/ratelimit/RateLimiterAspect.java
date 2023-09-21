package com.tyson.useless.system.ratelimit;
import com.tyson.useless.system.exception.RateLimitExceededException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimiterAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(rateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String key = "rate_limit:" + methodName;

        int permits = rateLimited.permits();
        int period = rateLimited.period();

        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count != null && count == 1) {
            redisTemplate.expire(key, period, TimeUnit.SECONDS);
        }

        if (count != null && count <= permits) {
            try {
                return joinPoint.proceed();
            } finally {
                redisTemplate.opsForValue().decrement(key, 1);
            }
        } else {
            throw new RateLimitExceededException("Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS.value());

        }
    }
}



