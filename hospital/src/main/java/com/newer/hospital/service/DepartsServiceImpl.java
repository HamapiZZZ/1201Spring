package com.newer.hospital.service;

import com.newer.hospital.domain.Departs;
import com.newer.hospital.mapper.DepartsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("departsService")
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class DepartsServiceImpl implements DepartsService{

    @Autowired
    private DepartsMapper departsMapper;

    @Override
    public List<Departs> find() {
        return departsMapper.find();
    }
}
