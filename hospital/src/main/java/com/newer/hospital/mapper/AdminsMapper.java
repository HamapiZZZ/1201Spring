package com.newer.hospital.mapper;

import com.newer.hospital.domain.Admins;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace(implementation = com.newer.hospital.util.RedisCache.class)
public interface AdminsMapper {

    @Select("select * from admins where aexist=1 and aname=#{aname}" +
            " and pwd=#{pwd}")
    Admins findByParam(@Param("aname")String aname,@Param("pwd")String pwd);

    @Update("update admins set login_time=now() where aid=#{aid}")
    int updateLoginTime(@Param("aid")Integer aid);

    @Update("update admins set pwd=#{pwd},lastpasswordresetdate=curdate() where aid=#{aid}")
    int updatePwd(@Param("aid")Integer aid,@Param("pwd")String pwd);


    @Select("select aid,aname,aexist,by1 from admins " +
            "where state=#{state}")
    List<Admins> findByState(@Param("state") Integer state);

    @Insert("insert into admins (aname,pwd,state,by1) values" +
            " (#{aname},#{pwd},#{state},#{by1})")
    int insertAdmins(Admins admins);

    @Insert("insert into user_authority values(@@Identity,#{authority_id})")
    int insertAdminsAuthority(@Param("authority_id")Integer authority_id);

    Admins findByName(@Param("aname")String aname);
}
