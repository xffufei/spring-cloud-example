package com.xf.ff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xf.ff.api.Demo1ApiClient;

/**
 * @author ff
 */
@RestController
@RequestMapping("demo2")
public class T2 {

    @Autowired
    private Demo1ApiClient demo1ApiClient;

    @RequestMapping("/test")
    public String hello(){
        System.err.println("demo2");
        return  "demo2";
    }

    @RequestMapping("/getdemo1")
    public String getdemo1(){

        System.err.println("获取demo1");

        return demo1ApiClient.test();
    }
}
