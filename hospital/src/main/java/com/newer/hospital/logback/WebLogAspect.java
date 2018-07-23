package com.newer.hospital.logback;

import com.mongodb.BasicDBObject;



import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Order(1)
//ORDER用于定义切面执行顺序
public class WebLogAspect {
    //定义日志记录器
    private static final Logger logger= LoggerFactory.getLogger("MONGODB");

    @Pointcut("execution(public * com.*.*.controller.*.*(..))")
    public void weblog(){
    }

    @Before("weblog()")
    public void dobefore(JoinPoint joinPoint){
        System.out.println("web前置请求");
        //获取servlet请求
        ServletRequestAttributes attributes=
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //mongodb保存的基本对象
        BasicDBObject object=getBasicDBObject(request,joinPoint);

        logger.info(object.toString());
    }

    private BasicDBObject getBasicDBObject(HttpServletRequest request,JoinPoint joinPoint){
        BasicDBObject b=new BasicDBObject();
        b.append("RequestURL",request.getRequestURL().toString());
        b.append("METHOD",request.getMethod());
        b.append("RemoteAddr",request.getRemoteAddr());
        b.append("HEADER",getHeader(request));
        b.append("CLASS_METHOD",joinPoint.getSignature()
                .getDeclaringTypeName()+"."+
                joinPoint.getSignature().getName());
        b.append("Args",Arrays.toString(joinPoint.getArgs()));
        return b;
    }

    private Map<String,String> getHeader(HttpServletRequest request){
        Map<String,String> maps=new HashMap<>();
        Enumeration<String> header=request.getHeaderNames();

        while (header.hasMoreElements()){
            String key=header.nextElement();
            maps.put(key,request.getHeader(key));
        }
        return maps;
    }

}
