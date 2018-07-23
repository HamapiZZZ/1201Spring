package com.newer.hospital.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    将日志信息输出到 mongodb的实现类
 */
@Component
public class MongoDBAppender extends UnsynchronizedAppenderBase<LoggingEvent>
implements ApplicationContextAware{


    private static LogRepository logRepository;


    @Override
    protected void append(LoggingEvent loggingEvent) {
        MyLog myLog=new MyLog();
        myLog.setLevel(loggingEvent.getLevel().toString());
        myLog.setMessage(loggingEvent.getFormattedMessage());
        myLog.setThread(loggingEvent.getThreadName());


        myLog.setTs(new Date());
        logRepository.save(myLog);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext.getAutowireCapableBeanFactory().
                getBean(LogRepository.class)!=null){
            logRepository=applicationContext.getAutowireCapableBeanFactory().
                    getBean(LogRepository.class);
        }
    }
}
