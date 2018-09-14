package com.sdyy.redis.redisdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.DruidPasswordCallback;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohu
 * @createDate 2018-09-14 10:23
 */
@Configuration
@PropertySource(value = "classpath:config/druid.properties")
public class DruidConfig {

    //将druid 加入
    @Bean
    public DataSource dataSource(){
        return  new DruidDataSource();
    }


    //druid监控
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String ,String> initMap = new HashMap<String,String>();
        initMap.put("loginUsername","admin");
        initMap.put("loginPassword","admin123");
        bean.setInitParameters(initMap);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<String,String>();
        initParams.put("exclusions","*.js,*.css,/druid/*,*.png,*.jpg,*.WD3,*.mp4,*.webm,*.Ogg");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }


    /***
     * 应用部署时密码加密
     */
 /*   @Bean(name="dbPasswordCallback")
    @Lazy(value = true)
    public DruidPasswordCallback dBPasswordCallback() {
        DBPasswordCallback dbPasswordCallback = new DBPasswordCallback();
        return dbPasswordCallback;
    }*/

}
