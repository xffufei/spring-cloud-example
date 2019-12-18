package com.xf.ff.config;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 日志处理类
 * 
 * @author liaohongjian
 *
 * @since 2017年3月27日
 *
 * @version 1.0
 */
@Aspect
@Component
public class RequestLogHandler {

	private static Logger logger = LoggerFactory.getLogger(RequestLogHandler.class);
	
	private ThreadLocal<Long> startTimeLocal = new ThreadLocal<Long>();
	
	
	public static final String REQUEST_ID = "_REQUEST_ID_";	
	
	public static final String REQUEST_LOG_INFO = "_REQUEST_LOG_INFO_";
	
	
	@Pointcut("execution(public * com.xf.ff..*Controller.*(..))")
	public void webLog() {

	}

	@Before(value="webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTimeLocal.set(System.currentTimeMillis());
		
		// 接收到请求，记录请求内容
		HttpServletRequest request = RequestHelper.getRequest();
		
		//header中是否存在requestId(请求传递)
		String requestId = request.getHeader(REQUEST_ID);
		
		//首次请求,创建请求标识
		if(requestId == null){
			requestId = UUID.randomUUID().toString();
		}
		
		//将UUID设置到Request的参数中
		request.setAttribute(REQUEST_ID,requestId);
		
		StringBuilder info = new StringBuilder();
		// 记录下请求内容
		info.append("[*=请求requestID==]>: ").append(requestId).append("\n");
		info.append("[==请求地址=======]>: ").append(request.getRequestURL().toString()).append("\n");
		info.append("[==请求方法=======]>: ").append(request.getMethod()).append("\n");
		info.append("[==客户IP========]>: ").append(request.getRemoteAddr()).append("\n");
		info.append("[==映射方法=======]>: ").append(joinPoint.getSignature().getDeclaringTypeName()).append(".").append(joinPoint.getSignature().getName()).append("\n");
		info.append("[==请求参数=======]>: ");
		
		//请求参数日志开始
		//文件上传不打印内容,输出文件大小
		long length = this.isfileupload(joinPoint);
		
		if( length> 0){
			info.append("上传文件大小为： " + length);
		}else{
			try{
				info.append(joinPoint.getArgs()== null ? "null" : joinPoint.getArgs());
			}catch(Exception e){
				info.append(joinPoint.getArgs()== null ? "null" : joinPoint.getArgs().toString());
			}
		}
		
		//请求参数日志结束
		info.append("\n");
		
		//logger.info("\n"+info.toString());
		request.setAttribute(REQUEST_LOG_INFO, info);
	}

	@AfterReturning(returning = "object", pointcut = "webLog()")
	public void doAfterReturning(Object object) throws Throwable {
		// 接收到请求，记录请求内容
		HttpServletRequest request = RequestHelper.getRequest();
		
		StringBuilder info =(StringBuilder)request.getAttribute(REQUEST_LOG_INFO);
		
		
		String requestId = (String)request.getAttribute(REQUEST_ID);
		
		//返回请求唯一标识
//		if(object != null && object instanceof ResponseResult<?> ){
//			((ResponseResult<?>)object).setRequestId( requestId );
//		}
		
		// 处理完请求，返回内容
		info.append("[==响应结果=======]>: ");
		try{
			info.append(object == null ? "" : object);
		}catch(Exception e){
			info.append(object == null ? "" : object.toString());
		}
		
		//响应结果日志输出结束
		info.append("\n");
		
		info.append("[==执行耗时=======]> : ").append(System.currentTimeMillis() - startTimeLocal.get()).append("ms").append("\n");
		
		logger.info(info.toString());
	}
	
	
	/**
	 * 判断是否文件上传。文件上传时返回文件大小，否则返回-1
	 * @param joinPoint
	 * @return
	 */
	private long isfileupload(JoinPoint joinPoint){
		
		Object[] args = joinPoint.getArgs();
		
		if(args == null || args.length == 0){
			return -1;
		}
		
		for(Object arg : args){
			if(arg == null){
				continue;
			}
			
			if(arg instanceof MultipartFile){
				long size = ((MultipartFile)arg).getSize();
				return size;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * 获取请求的日志输出ID
	 * @return
	 */
	public static String getRequestId() {
		// 接收到请求，记录请求内容
		HttpServletRequest request = RequestHelper.getRequest();

		return request == null ? null : (String)request.getAttribute(REQUEST_ID);
	}
}
