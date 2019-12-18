package com.xf.ff.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 请求响应辅助工具
 * 
 * @author liaohongjian
 *
 * @since 2017年6月22日
 *
 * @version 1.0
 */
public class RequestHelper {
	
	public static final String CURRENT_USER = "CURRENT_AUTHORIZATION_USER_";

	/**
	 * 获取 HttpServletRequest实例
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes == null ? null :  attributes.getRequest();
	}
	
	/**
	 * 获取 HttpServletResponse实例
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes == null ? null : attributes.getResponse();
	}
	
	/**
	 * 获取 HttpSession实例
	 * @return
	 */
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 判断请求是否是异步请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(){
		HttpServletRequest request  = getRequest();
		
		if (( (request.getHeader("accept") !=null && request.getHeader("accept").indexOf("application/json") > -1) || 
				(request.getHeader("X-Requested-With")!= null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {  
           
			return true;
        }else{
        	return false;
        }
	}
	
	/**
	 * 判断请求是否直接由地址栏发出
	 * @param request
	 * @return
	 */
	public static boolean isRefererRequest(){
		
		HttpServletRequest request  = getRequest();
		
		//地址栏的为GET请求
		if( request.getMethod().equals( "POST" )){
			return false;
		}
		
		//地址栏请求时,Referer为空
		if(request.getHeader("Referer") == null ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断浏览器的类型(IE/non IE)
	 * @param request
	 * @return 
	 */
	public static boolean isIEbrowser( ){
		HttpServletRequest request  = getRequest();
		
		String userAgent = request.getHeader("user-agent");
		
		System.out.println("userAgent===" + userAgent);
		
		//IE 判断 
		if (userAgent.contains("MSIE")||userAgent.contains("Trident") || userAgent.contains("Edge")){
			return true;
		}
		//Mozilla(火狐,chrome...)
		return false;
	}
	
	/**
	 * 
	 * 获取请求头信息中的 Authorization
	 * 
	 * @author liaohongjian
	 * @since 2017年7月15日
	 * @return
	 */
	public static String getAuthorization(){
		return getRequest().getHeader("Authorization");
	}
	
	
	/**
	 * 获取请求token参数 
	 * 
	 * @see #getAuthorization()
	 * 
	 * @author liaohongjian
	 * @since 2017年7月15日
	 * @return
	 */
	public static String getAccessToken(){
		return getAuthorization();
	}
	
	/**
	 * 获取请求服务
	 * 
	 * @author liaohongjian
	 * @since 2017年7月17日
	 * @return
	 */
	public static String getRequestURL(){
		return getRequest().getRequestURL().toString();
	}
	
	
	/**
	 * 输出ResponseResult响应结果
	 * 
	 * <p> 默认按 ;
	 * 
	 * @author liaohongjian
	 * @since 2017年7月18日
	 * @param result
	 */
	public static void write(Object result){
		HttpServletResponse response = getResponse();
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		try {
			response.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
