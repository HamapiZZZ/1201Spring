package com.newer.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private static String[] orgins=new String[]{
            "localhost:8080",
            "127.0.0.1:8080"
    };

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        //跨域请求访问配置
        CorsConfiguration configuration=new CorsConfiguration();
        for(String url:orgins){
            configuration.addAllowedOrigin("http://"+url);
            configuration.addAllowedOrigin("https://"+url);
        }
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        //configuration.setAllowCredentials(true);响应数据支持跨域访问
        configuration.addAllowedOrigin("*");//允许任何来源的跨域访问
        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
    }

}
