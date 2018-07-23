package com.newer.hospital.service;

public interface RegistrationService {

    int getToday(Integer deid);

    int getYestoday(Integer deid);

    int getWeek(Integer deid);

    int getMonth(Integer deid);

    int getQuarter(Integer deid);
}
