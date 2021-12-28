package com.lec.foodmarket.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		// 로그아웃 시간 남기기
		LocalDateTime logoutTime = LocalDateTime.now();
		System.out.println("로그아웃시간: " + logoutTime);
		// 사용시간(체류시간) 계산
		LocalDateTime loginTime = (LocalDateTime)request.getSession().getAttribute("loginTime");
		if(loginTime != null) {
			long seconds = loginTime.until(logoutTime, ChronoUnit.SECONDS);
			System.out.println("사용시간: " + seconds + " 초");
		}
		request.getSession().invalidate();
		
		String redirectUrl = "/layout/user/index";
		
		// ret_url 이 있는 경우 logout 하고 해당 url 로 redirect
		if(request.getParameter("ret_url") != null) {
			redirectUrl = request.getParameter("ret_url"); 
		}
		
		response.sendRedirect(redirectUrl);
	}

}



















