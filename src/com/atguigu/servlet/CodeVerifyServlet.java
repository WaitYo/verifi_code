package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atguigu.jedis.utils.VerifyCodeConfig;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CodeVerifyServlet
 */
public class CodeVerifyServlet extends HttpServlet {
	
 
    
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeVerifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phoneNo = request.getParameter("phone_no");  
		String verifyCode = request.getParameter("verify_code");
		//��redis��ѯ �Ѵ����֤�� 
		if(phoneNo==null||verifyCode==null){
			return ;
		}
		
		Jedis jedis =new Jedis(VerifyCodeConfig.HOST, VerifyCodeConfig.PORT);
		
		String phoneKey=VerifyCodeConfig.PHONE_PREFIX+phoneNo+VerifyCodeConfig.PHONE_SUFFIX;
		
		String expectedCode=jedis.get(phoneKey);
		
		jedis.close();
		if( verifyCode.equals(expectedCode) ){
			
			response.getWriter().print(true);
			return;
		}
		
		return ;
		
		
		
	}

}
