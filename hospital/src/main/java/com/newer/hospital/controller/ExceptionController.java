package com.newer.hospital.controller;

import com.newer.hospital.domain.ErrorInfo;
import com.newer.hospital.exception.HospitalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(value = HospitalException.class)
    public ResponseEntity<?> hanlerException(HttpServletRequest request,HospitalException e)throws Exception{
        ErrorInfo<String> error=new ErrorInfo<>();
        error.setCode(ErrorInfo.ERROR);
        error.setMessage(e.getMessage());
        error.setUrl(request.getRequestURL().toString());
        error.setData("some data");
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
