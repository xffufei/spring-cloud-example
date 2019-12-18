package com.xf.ff;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ff
 */
@RestController
@RequestMapping("web")
public class T1 {

    @PostMapping("/test")
    public String hello(){
        System.err.println("hello,world");
        return  "hello,world";
    }
}
