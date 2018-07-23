package com.newer.hospital;

import com.newer.hospital.domain.Admins;
import com.newer.hospital.mapper.AdminsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminsTest {

    @Autowired
    private AdminsMapper adminsMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test(){
        Admins admins=adminsMapper.findByParam("admin","111111");
        System.out.println(admins.getLoginTime());
    }

    @Test
    public void test1(){
        Admins admins=adminsMapper.findByName("admin");
        System.out.println(admins.getPwd());
    }

    @Test
    public void testPassword(){
        System.out.println(passwordEncoder.encode("admin"));
    }



}
