package com.atguigu.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.jedis.utils.VerifyCodeConfig;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class VerifiCodeServlet
 */
public class CodeSenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeSenderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
     
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNo = request.getParameter("phone_no");
		
		//发送前校验
		if(phoneNo==null){
			return ;
		}
		Jedis jedis =new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		
		
		//计数器
		String countKey=VerifyCodeConfig.PHONE_PREFIX+phoneNo+VerifyCodeConfig.COUNT_SUFFIX;
		String countStr = jedis.get(countKey);
		
		if(countStr!=null){
			int count = Integer.valueOf(countStr);
			if(count>=3){
				response.getWriter().print("limit");
				jedis.close();
				return;
			}else{
				jedis.incr(countKey);
			}
		}else{
			jedis.setex(countKey, VerifyCodeConfig.SECONDS_PER_DAY, "1");
		}
		
		
		String verifyCode = genCode(6);
		
	
		  //验证码
		String phoneKey=VerifyCodeConfig.PHONE_PREFIX+phoneNo+VerifyCodeConfig.PHONE_SUFFIX;
		jedis.setex(phoneKey,VerifyCodeConfig.CODE_TIMEOUT ,verifyCode);
		
		
		System.out.println(verifyCode);
		
		//SmsUtil.sendSms(phoneNo, verifyCode);
		jedis.close();
		 
		response.getWriter().print(true);
	} 
	
	
	private   String genCode(int len){
		 String code="";
		 for (int i = 0; i < len; i++) {
		     int rand=  new Random().nextInt(10);
		     code+=rand;
		 }
		 
		return code;
	}
	
	
 
}
