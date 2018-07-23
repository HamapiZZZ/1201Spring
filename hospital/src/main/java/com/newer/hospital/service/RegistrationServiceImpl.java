package com.newer.hospital.service;

import com.newer.hospital.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("registrationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public int getToday(Integer deid) {
        return registrationMapper.getToday(deid);
    }

    @Override
    public int getYestoday(Integer deid) {
        return registrationMapper.getYestoday(deid);
    }

    @Override
    public int getWeek(Integer deid) {
        return registrationMapper.getWeek(deid);
    }

    @Override
    public int getMonth(Integer deid) {
        return registrationMapper.getMonth(deid);
    }

    @Override
    public int getQuarter(Integer deid) {
        return registrationMapper.getQuarter(deid);
    }
}
