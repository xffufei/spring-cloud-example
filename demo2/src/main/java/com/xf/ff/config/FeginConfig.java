package com.xf.ff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Feign.Builder;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;

@Configuration
public class FeginConfig {
	
    //header部分增加requestId传递到下层服务
    @Bean
    public Builder feginBuild(){
    	Builder build = Feign.builder();
    	build.requestInterceptor(new ForwardedForInterceptor());
    	return build;
    }
    
    @Bean
	Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}
    
    @Bean
    Request.Options feignOptions(){
    	return new Request.Options(10 * 1000, 60 * 1000);
    }
    
    //只显示请求URL,返回状态
    @Bean
    Logger.Level feignLoggerLevel(){
    	return Logger.Level.BASIC;
    }
    
    static class ForwardedForInterceptor implements RequestInterceptor { // 实现自己的RequestInterceptor
	  	  @Override 
	  	  public void apply(RequestTemplate template) {
	  		template.header(RequestLogHandler.REQUEST_ID, RequestLogHandler.getRequestId());
	  	  }
      }
}
