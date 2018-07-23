package com.newer.hospital.util;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
    使用redis实现mybatis二级缓存
 */
public class RedisCache implements Cache{

    //定义日志记录器
    private static final Logger logger= LoggerFactory.getLogger(RedisCache.class);
    //缓存对象唯一标识
    private final String id;
    //用于事务性缓存操作的读写锁
    private static ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    //用于将缓存对象写入Redis的模板对象
    private RedisTemplate redisTemplate;
    //定义缓存对象失效时间
    private static final long EXPRIRE_TIME_IN_MINUT=30;

    public RedisCache(String id){
        if(id==null){
            throw new IllegalArgumentException("缓存对象id不能为空");
        }
        this.id=id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    /*
        保存缓存对象
     */
    @Override
    public void putObject(Object key, Object value) {
        try {
            RedisTemplate redisTemplate=getRedisTemplate();
            //得到值操作对象
            ValueOperations operations=redisTemplate.opsForValue();
            //设置缓存对象
            operations.set(key,value,EXPRIRE_TIME_IN_MINUT, TimeUnit.MINUTES);
            logger.debug("缓存对象保存成功!");
        }catch (Throwable t){
            logger.error("缓存对象保存失败!"+t);
        }
    }

    /*
         获取缓存对象
     */
    @Override
    public Object getObject(Object key) {
        try {
            RedisTemplate redisTemplate=getRedisTemplate();
            //得到值操作对象
            ValueOperations operations=redisTemplate.opsForValue();
            Object result=operations.get(key);
            logger.debug("获取缓存对象成功！");
            return result;
        }catch (Throwable t){
            logger.error("缓存对象获取失败!"+t);
            return null;
        }
    }

    /*
        删除缓存对象
     */
    @Override
    public Object removeObject(Object key) {
        try {
            RedisTemplate redisTemplate=getRedisTemplate();
            redisTemplate.delete(key);
            logger.debug("删除缓存对象成功！");
        }catch (Throwable t){
            logger.error("删除缓存对象失败!"+t);
        }
        return null;
    }

    /*
        清空缓存对象
     */
    @Override
    public void clear() {
        RedisTemplate redisTemplate=getRedisTemplate();
        redisTemplate.execute((RedisCallback)collection->{
            collection.flushDb();
            return null;
        });
        logger.debug("清空缓存对象成功！");
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private RedisTemplate getRedisTemplate(){
        if(redisTemplate==null){
            redisTemplate=ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
