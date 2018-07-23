package com.newer.hospital;

import com.newer.hospital.domain.Admins;
import com.newer.hospital.domain.Departs;
import com.newer.hospital.mapper.AdminsMapper;
import com.newer.hospital.mapper.DepartsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
    @Autowired
    private DepartsMapper departsMapper;

    @Autowired
    private AdminsMapper adminsMapper;


    @Test
    public void test1(){
        List<Departs> list1=departsMapper.find();
        System.out.println("第一次查询:"+list1.size());
        List<Departs> list2=departsMapper.find();
        System.out.println("第二次查询:"+list2.size());
        List<Departs> list3=departsMapper.find();
        System.out.println("第三次查询:"+list3.size());
    }

    @Test
    public void test2(){
        List<Admins> list=adminsMapper.findByState(0);

        System.out.println("第一次查");

        System.out.println("lzp查");
        List<Admins> list2=adminsMapper.findByState(1);


//        Admins admins=adminsMapper.findByName("admin");
//        System.out.println("第一次查");
//        Admins admins1=adminsMapper.findByName("admin");
//        System.out.println("第二次查");
    }

}
