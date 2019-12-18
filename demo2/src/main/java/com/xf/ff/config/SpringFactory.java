package com.xf.ff.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 提供静态获取Spring bean 的工厂方法
 * 
 * @author liaohongjian
 *
 * @since 2017年3月27日
 *
 * @version 1.0
 */
@Component
public class SpringFactory implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		applicationContext =  ctx;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> beanClass ){
		return applicationContext.getBean(beanClass);
	}
	
	public static <T> T getBean(String beanName,Class<T> beanClass) {
		return applicationContext.getBean(beanName,beanClass);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		return (T) applicationContext.getBean(beanName);
	}
}
