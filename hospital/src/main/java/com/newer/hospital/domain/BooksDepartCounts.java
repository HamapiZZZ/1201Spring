package com.newer.hospital.domain;

import java.io.Serializable;
import java.util.List;

public class BooksDepartCounts implements Serializable{

    private String dename;//科室名
    private List<Integer> countList;//每个时间段挂号人数集合

    public String getDename() {
        return dename;
    }

    public void setDename(String dename) {
        this.dename = dename;
    }

    public List<Integer> getCountList() {
        return countList;
    }

    public void setCountList(List<Integer> countList) {
        this.countList = countList;
    }
}
