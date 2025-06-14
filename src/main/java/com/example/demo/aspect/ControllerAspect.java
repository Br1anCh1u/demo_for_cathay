package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void controllerLayer() {}


//    @Around("controllerLayer()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("[around] [controller] [Thread-{}] [http method: {}] [repository: {}] [duration: {} ms] [uri: {}] [args: {}]",
                Thread.currentThread().getId(),
                request.getMethod(),
                pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName(),
                elapsedTime,
                request.getRequestURI(),
                Arrays.toString(pjp.getArgs()));
        return output;
    }

//    @Before("controllerLayer()")
    public void before(JoinPoint joinPoint) {
        log.info("[before] [controller] [Thread-{}] [controller: {}] [args: {}]",
                Thread.currentThread().getId(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

//    @AfterReturning(value = "controllerLayer()", returning = "retVal")
    public void afterReturning(JoinPoint joinPoint, Object retVal) {
        log.info("[afterReturning] [controller] [Thread-{}] [controller: {}] [return: {}]",
                Thread.currentThread().getId(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                retVal);
    }

//    @AfterThrowing(value = "controllerLayer()", throwing = "ex")
    public void handleException(Exception ex) {
        log.error("[controller error]", ex);
    }
}
