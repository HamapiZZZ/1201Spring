package com.newer.hospital.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = com.newer.hospital.util.RedisCache.class)
public interface RegistrationMapper {

    @Select("SELECT COUNT(rid) FROM " +
            "  registration RE JOIN bookable B " +
            "   ON RE.bid=B.bid JOIN doctors DO " +
            "   ON B.doid=DO.doid JOIN departs DE " +
            "    ON DO.deid=DE.deid " +
            "      WHERE DE.deid=#{deid} and to_days(bdate)=to_days(now())")
    //查询今天指定科室的挂号人数
    int getToday(@Param("deid")Integer deid);

    @Select("SELECT COUNT(rid) FROM " +
            "  registration RE JOIN bookable B " +
            "   ON RE.bid=B.bid JOIN doctors DO " +
            "   ON B.doid=DO.doid JOIN departs DE " +
            "    ON DO.deid=DE.deid " +
            "      WHERE DE.deid=#{deid} and to_days(now())-to_days(bdate)=1")
        //查询昨天指定科室的挂号人数
    int getYestoday(@Param("deid")Integer deid);

    @Select("SELECT COUNT(rid) FROM " +
            "  registration RE JOIN bookable B " +
            "   ON RE.bid=B.bid JOIN doctors DO " +
            "   ON B.doid=DO.doid JOIN departs DE " +
            "    ON DO.deid=DE.deid " +
            "      WHERE DE.deid=#{deid} and yearweek(bdate)=yearweek(now())")
        //查询本周指定科室的挂号人数
    int getWeek(@Param("deid")Integer deid);

    @Select("SELECT COUNT(rid) FROM " +
            "  registration RE JOIN bookable B " +
            "   ON RE.bid=B.bid JOIN doctors DO " +
            "   ON B.doid=DO.doid JOIN departs DE " +
            "    ON DO.deid=DE.deid " +
            "      WHERE DE.deid=#{deid} and date_format(bdate,'%Y%m')=date_format(now(),'%Y%m')")
        //查询本月指定科室的挂号人数
    int getMonth(@Param("deid")Integer deid);

    @Select("SELECT COUNT(rid) FROM " +
            "  registration RE JOIN bookable B " +
            "   ON RE.bid=B.bid JOIN doctors DO " +
            "   ON B.doid=DO.doid JOIN departs DE " +
            "    ON DO.deid=DE.deid " +
            "      WHERE DE.deid=#{deid} and quarter(now())=quarter(bdate)")
        //查询本季度指定科室的挂号人数
    int getQuarter(@Param("deid")Integer deid);


}
