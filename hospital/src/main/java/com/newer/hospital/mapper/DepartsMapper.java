package com.newer.hospital.mapper;

import com.newer.hospital.domain.Departs;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(implementation = com.newer.hospital.util.RedisCache.class)
public interface DepartsMapper {

    @Select("select * from departs where deexist=1")
    //查询所有没被禁用的科室信息
    List<Departs> find();
}
