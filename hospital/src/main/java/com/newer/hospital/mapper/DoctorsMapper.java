package com.newer.hospital.mapper;

import com.newer.hospital.domain.Doctors;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = com.newer.hospital.util.RedisCache.class)
public interface DoctorsMapper {

    @Select("select doid,title from doctors where doid=#{doid}")
    Doctors findById(@Param("doid")Integer doid);
}
