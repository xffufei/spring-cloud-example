package com.xf.ff;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ff
 */
@RestController
@RequestMapping("demo1")
public class T1 {

    @PostMapping("/test")
    public String hello(){
        System.err.println("demo1");
        return  "demo1";
    }
}
