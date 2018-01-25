package com.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class httpAspect {

    private final static Logger logger = LoggerFactory.getLogger(httpAspect.class);

    @Pointcut("execution(public * com.demo.controller.*.*(..))")
    public void log() {
    }

    /*
    * 请求之前
    * */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // url
        logger.info("请求url:{}", request.getRequestURL());

        // method
        logger.info("请求方式:{}", request.getMethod());

        // ip
        logger.info("请求ip:{}", request.getRemoteAddr());

        // 类方法
        logger.info("调用方法={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // params
        logger.info("参数={}", joinPoint.getArgs());
    }

    /*
    *请求之后
    * */
    @After("log()")
    public void doAfter() {
        logger.info("http请求完毕");
    }

    /*
    * 返回值
    * */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
    }
}
