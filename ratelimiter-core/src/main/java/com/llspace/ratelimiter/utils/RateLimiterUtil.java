package com.llspace.ratelimiter.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>@filename RateLimiterUtil</p>
 * <p>
 * <p>@description RateLimiter工具类</p>
 *
 * @author liyupeng
 * @version 1.0
 * @since 2019/6/4 17:32
 **/
public class RateLimiterUtil {
    /**
     * 获取唯一标识此次请求的key
     *
     * @param joinPoint     切点
     * @param type          限流类型
     * @return key
     */
    public static String getKey(String algorithm, JoinPoint joinPoint, RateLimitType type) {
        String prefix = Constant.PREFIX;
        if(algorithm.contains("redis")){
            prefix = Constant.REDIS_PREFIX;
        }
        StringBuffer key = new StringBuffer(prefix);
        //以方法名加参数列表作为唯一标识方法的key
        if (RateLimitType.ALL.equals(type)) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            key.append(joinPoint.getTarget().getClass().getName() + ".");
            key.append(signature.getMethod().getName() + "()");
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //以用户信息作为key
        if (RateLimitType.USER.equals(type)) {
            if (request.getUserPrincipal() != null) {
                key.append(request.getUserPrincipal().getName());
            } else {
                //异常处理
            }
        }
        //以IP地址作为key
        if (RateLimitType.IP.equals(type)) {
            key.append(getIpAddr(request));
        }
        //以自定义内容作为key
        if (RateLimitType.CUSTOM.equals(type)) {
            if (request.getAttribute(Constant.CUSTOM) != null) {
                key.append(request.getAttribute(Constant.CUSTOM).toString());
            } else {
                //异常处理
            }
        }
        return key.toString();
    }

    /**
     * 获取当前请求的ip
     *
     * @param request HttpServletRequest
     * @return ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
