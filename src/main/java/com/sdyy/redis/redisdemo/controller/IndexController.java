package com.sdyy.redis.redisdemo.controller;

import com.sdyy.redis.redisdemo.model.Person;
import com.sdyy.redis.redisdemo.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaohu
 * @createDate 2018-09-13 19:45
 */
@RestController
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/index")
    public Person person(){
        Person p = new Person("xiaohu","man",30);
        List list  = new ArrayList();
        list.add(p);
        redisService.putList("xiaohu",list);
        return p;
    }

    @RequestMapping("/get")
    public String getList(){

        return redisService.getList("xiaohu").toString();
    }
}
