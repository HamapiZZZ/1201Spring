package com.newer.hospital.service;

import com.newer.hospital.domain.Admins;

import java.util.List;

public interface AdminsService {

    Admins login(String aname,String pwd);

    int updatePwd(Integer aid,String pwd);

    List<Admins> findByState(Integer state);

    int addGeneralAdmins(Admins admins);
}
