package com.newer.hospital.service;

import com.newer.hospital.domain.Admins;
import com.newer.hospital.mapper.AdminsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("adminsService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class AdminsServiceImpl implements AdminsService{

    @Autowired
    private AdminsMapper adminsMapper;

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public Admins login(String aname, String pwd) {
        Admins admins=adminsMapper.findByParam(aname,pwd);
        if(admins!=null){
            adminsMapper.updateLoginTime(admins.getAid());
        }
        return admins;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public int updatePwd(Integer aid, String pwd) {

        return adminsMapper.updatePwd(aid,pwd);
    }

    @Override
    public List<Admins> findByState(Integer state) {
        return adminsMapper.findByState(state);
    }

    @Override
    public int addGeneralAdmins(Admins admins) {
        //添加普通管理员
        int i=adminsMapper.insertAdmins(admins);
        if(i>0){
            adminsMapper.insertAdminsAuthority(1);
        }
        return i;
    }
}
