package com.newer.hospital.controller;

import com.newer.hospital.exception.HospitalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GlobalController {

    @RequestMapping("/testError")
    public ResponseEntity<?> testError() throws HospitalException {
        throw new HospitalException("医院自定义异常!");
    }
}
