package com.newer.hospital.controller;


import com.newer.hospital.domain.BooksDepartCounts;
import com.newer.hospital.domain.Departs;
import com.newer.hospital.domain.DepartsBooks;
import com.newer.hospital.service.DepartsService;
import com.newer.hospital.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
//授权普通用户访问
@PreAuthorize("hasRole('USER')")
public class RegistrationController {

    @Autowired
    private DepartsService departsService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/booksdepart",method = RequestMethod.GET)
    public ResponseEntity<?> booksdepart(){
        //查询可用科室集合
        List<Departs> departs=departsService.find();
        //所有科室挂号数据集合
        List<BooksDepartCounts> list=new ArrayList<>();

        //遍历科室集合
        for(Departs departs1:departs){
            //单个科室挂号数据
            BooksDepartCounts bdc=new BooksDepartCounts();
            List<Integer> countList=new ArrayList<>();

            countList.add(registrationService.getToday(departs1.getDeid()));
            countList.add(registrationService.getYestoday(departs1.getDeid()));
            countList.add(registrationService.getWeek(departs1.getDeid()));
            countList.add(registrationService.getMonth(departs1.getDeid()));
            countList.add(registrationService.getQuarter(departs1.getDeid()));

            bdc.setDename(departs1.getDename());
            bdc.setCountList(countList);

            list.add(bdc);
        }

        DepartsBooks books=new DepartsBooks();

        List<String> times=new ArrayList<>();
        times.add("今天");
        times.add("昨天");
        times.add("本周");
        times.add("本月");
        times.add("本季度");

        books.setTimes(times);
        books.setBooksDepartCounts(list);
        return new ResponseEntity<DepartsBooks>(books, HttpStatus.OK);
    }

    @RequestMapping("/drawDept")
    public ResponseEntity<?> drawDept(){
        //主柱状图数据集合:各个科室本季度挂号数据
        List<Map<String,Object>> dataList=new ArrayList<>();
        //子柱状图数据集合：所有科室五个时间段的挂号数据
        List<Map<String,Object>> seriesList=new ArrayList<>();
        //查询科室集合
        List<Departs> departs=departsService.find();
        //遍历科室集合
        for(Departs departs1:departs){
            //单个主柱状图数据
            Map<String,Object> maindata=new HashMap<>();

            int counts[]=new int[5];
            counts[0]=registrationService.getQuarter(departs1.getDeid());
            counts[1]=registrationService.getMonth(departs1.getDeid());
            counts[2]=registrationService.getWeek(departs1.getDeid());
            counts[3]=registrationService.getYestoday(departs1.getDeid());
            counts[4]=registrationService.getToday(departs1.getDeid());

            maindata.put("name",departs1.getDename());
            maindata.put("y",counts[0]);
            maindata.put("drilldown",departs1.getDeid());//与子状图数据关联的id值
            //添加单个柱状图到主数据集合
            dataList.add(maindata);

            Map<String,Object> seriers=new HashMap<>();
            //子柱数据集合
            List<List<Object>> sdataList=new ArrayList<>();
            String[] times=new String[]{"本季度","本月","本周","昨天","今天"};
            for(int i=0;i<times.length;i++){
                List<Object> data=new ArrayList<>();
                data.add(times[i]);
                data.add(counts[i]);
                sdataList.add(data);
            }
            //某科室各时间段挂号数量
            seriers.put("name",departs1.getDename());
            seriers.put("id",departs1.getDeid());
            seriers.put("data",sdataList);
            seriesList.add(seriers);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("dataList",dataList);
        result.put("seriesList",seriesList);
        return new ResponseEntity<>(result,HttpStatus.OK);

    }
}
