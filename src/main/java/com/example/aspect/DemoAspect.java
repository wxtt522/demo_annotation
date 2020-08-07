package com.example.aspect;

/**
 * @Description:
 * @Author: wulh
 * @Date: 2020/8/7 10:59
 */

import com.example.annotation.Track;
import com.example.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@Aspect
public class DemoAspect {
    /**
     * 只要用到了MyLog这个注解的，就是目标类
     */
    @Pointcut("@annotation(com.example.annotation.Track)")
    private void MyValid() {
    }

    /**
     * 环绕通知 ProceedingJoinPoint 执行proceed方法的作用是让目标方法执行，这也是环绕通知和前置、后置通知方法的一个最大区别。
     * ProceedingJoinPoint 继承了 JoinPoint 。是在JoinPoint的基础上暴露出 proceed 这个方法。proceed很重要，这个是aop代理链执行的方法。
     *
     * @param joinPoint
     */
    @Around("MyValid()")
    public void before(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("---------进去拦截器---------");
        log.info("[" + signature.getName() + " : start.....]");
        Track track = signature.getMethod().getAnnotation(Track.class);
        log.info("【目标对象方法被调用时候产生的日志，记录到日志表中】：" + track.eventType());
        String propertyName = track.propertyName();
        log.info("class=" + joinPoint.getSignature().getDeclaringTypeName() +
                "and method name=" + joinPoint.getSignature().getName());
        //获取请求头内容
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        doRequest(request);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        doResponse(response);
        //获取返还对象
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数=" + arg + " ");
        }
        try {
            Object object = joinPoint.proceed();

            log.info("返回值=" + object);
            log.info("code=" + ClassUtil.getPropertyValue(object, "code"));
            log.info("msg=" + ClassUtil.getPropertyValue(object, "msg"));
            Object data = ClassUtil.getPropertyValue(object, "data");
            log.info("data=" + data);
            log.info("自定义参数" + ClassUtil.getPropertyValue(data, propertyName));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void doRequest(HttpServletRequest request) {

        //URL：根据请求对象拿到访问的地址
        log.info("url=" + request.getRequestURL());
        //获取请求的方法，是Get还是Post请求
        log.info("method=" + request.getMethod());
        //ip：获取到访问
        log.info("ip=" + request.getRemoteAddr());
        //获取被拦截的类名和方法名
    }

    public void doResponse(HttpServletResponse response) {
        log.info(String.valueOf(response.getStatus()));
    }
}
