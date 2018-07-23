package com.newer.hospital.security.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.newer.hospital.domain.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/*
    spring security框架服务的用户类
 */
public class JwtUser implements UserDetails{

    private final Integer id;//必须
    private final String username;//必须
    private final String password;//必须
    private final Integer state;
    private final String email;
    private final Date lastPasswordResetDate;
    private final boolean enabled;//必须
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private final Date loginTime;
    //授权的角色集合
    private final Collection<? extends GrantedAuthority> authorities;//必须

    public JwtUser(Integer id, String username, String password, Integer state, String email, Date lastPasswordResetDate, boolean enabled, Date loginTime, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.state = state;
        this.email = email;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.enabled = enabled;
        this.loginTime = loginTime;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public Date getLoginTime() {
        return loginTime;
    }


}
