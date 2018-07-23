package com.newer.hospital.security.domain;

import com.newer.hospital.domain.Admins;
import com.newer.hospital.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory(){}

    public static JwtUser create(Admins user){
        return new JwtUser(user.getAid(),
                user.getAname(),
                user.getPwd(),
                user.getState(),
                user.getEmail(),
                user.getLastPasswordResetDate(),
                user.getAexist()==1?true:false,
                user.getLoginTime(),
                mapToGrantedAuthorities(user.getAuthorities())
        );
    }

    /*
        将查询的用户角色集合转换成security框架授权的角色集合
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
