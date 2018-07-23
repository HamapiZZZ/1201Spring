package com.newer.hospital.controller;

import com.newer.hospital.domain.Admins;
import com.newer.hospital.domain.Doctors;
import com.newer.hospital.security.JwtTokenUtil;
import com.newer.hospital.security.domain.JwtUser;
import com.newer.hospital.security.service.JwtUserDetailsService;
import com.newer.hospital.service.AdminsService;
import com.newer.hospital.service.DoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
@RequestMapping("/api")
public class AdminsController {

    @Autowired
    private AdminsService adminsService;

    @Value("${jwt.header}")
    private String header;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorsService doctorsService;

    @Deprecated
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ResponseEntity<?> login(@RequestParam("aname")String aname,
                                   @RequestParam("pwd")String pwd,
                                   HttpSession session){
        Admins admins=adminsService.login(aname,pwd);
        if(admins==null){
            return new ResponseEntity<CustomerErrorType>(new CustomerErrorType("用户或密码错误！"),HttpStatus.OK);
        }else{
            if(admins.getState()==2){
                //登录用户是医生，查询医生的职称，设置在登录用户的by1属性
                Doctors doctors=doctorsService.findTitle(admins.getDoid());
                admins.setBy1(doctors.getTitle());
            }
            session.setAttribute("user",admins);
            return new ResponseEntity<Admins>(admins, HttpStatus.OK);
        }
    }

    @Deprecated
    @RequestMapping(value = "/usersession",method = RequestMethod.GET)
    public ResponseEntity<?> getUserSession(HttpSession session){
        Admins user=(Admins)session.getAttribute("user");
        return new ResponseEntity<Admins>(user,HttpStatus.OK);
    }

    @RequestMapping(value = "/changepwd",method = RequestMethod.PUT)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePwd(@RequestParam("password")String password, HttpServletRequest request){
        //获取令牌
        String token=request.getHeader(header).substring(7);
        String username=jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user=(JwtUser)jwtUserDetailsService.loadUserByUsername(username);

        String pwd=passwordEncoder.encode(password);
        adminsService.updatePwd(user.getId(),pwd);
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Deprecated
    @RequestMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        session.removeAttribute("user");
        session.invalidate();
        return new ResponseEntity<>("ok",HttpStatus.OK);
    }



    @RequestMapping(value = "/admins/{state}",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByState(@PathVariable("state")Integer state){
        List<Admins> list=adminsService.findByState(state);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/admins",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAdmins(@RequestBody Admins admins){
        admins.setState(1);
        admins.setPwd(passwordEncoder.encode(admins.getPwd()));
        int result=adminsService.addGeneralAdmins(admins);
        Map<String,Object> map=new HashMap<>();
        map.put("result",result);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }




}
