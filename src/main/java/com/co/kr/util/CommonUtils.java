package com.co.kr.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.NetPermission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;



@Component
public class CommonUtils {
	//날짜
	public static String currentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		Date currentDate = new Date();
		return sdf.format(currentDate);
	}
	
	//get MacAddress
	public static String getMacAddress() {
        String result= "";
        InetAddress ipAdd;
        
        try {
            ipAdd = InetAddress.getLocalHost();
        	System.out.println(ipAdd);
            
        	//NetPermission np = new NetPermission("setSecurityManager");
            
            //NetworkInterface network = NetworkInterface.getByInetAddress(ipAdd);
        	NetworkInterface network = NetworkInterface.getByName("en0");
            System.out.println(network);
           
            byte[] macAddress = network.getHardwareAddress();
            
            
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < macAddress.length; i++) {
                sb.append(String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : ""));
            }
                result = sb.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e){
            e.printStackTrace();
        }
            
        return result;
     };
	
	
	//get IP
	 public static String getClientIP(HttpServletRequest req) {	
		String ip =req.getHeader("X-Forwarded-For");
		
		if(ip == null) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if(ip == null) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null) {
			ip = req.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null) {
			ip = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null) {
			ip = req.getRemoteAddr();
		}
		if(ip.equals("0:0:0:0:0:0:0:1")) {
			ip = ip.replace("0:0:0:0:0:0:0:1", "127.0.0.1");
		}
		
		return ip;		
	};
	
	
	
	
	// auth redirect
	public static void redirect(String alertText, String redirectPath, HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ModelAndView mav = new ModelAndView();
		
		//개발용 리다이렉트
		out.println("<script>alert('"+ alertText +"'); location.href='" + redirectPath + "'</script>");
		out.flush();
	}
	
	
}
