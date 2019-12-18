package com.xf.ff.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ff
 */
@FeignClient(name = "demo1")
public interface Demo1ApiClient {

    /**
     * 测试
     * @return
     */
    @RequestMapping(value = "/demo1/test", method = RequestMethod.POST)
    String test();
}
