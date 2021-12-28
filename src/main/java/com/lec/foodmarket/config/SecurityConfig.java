package com.lec.foodmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

// 스프링 시큐리티 설정
@Configuration // 빈 생성
@EnableWebSecurity // Web Secutiry 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 등록할 필터 객체

	// 비밀번호 암호화(일방향)
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 더블슬래시 허용
	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.httpFirewall(defaultHttpFirewall())
			.ignoring().antMatchers("/h2-console/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers()
			.frameOptions()
			.sameOrigin();
		// HttpSecurity 객체
		http
			.csrf().disable()
			.authorizeRequests()
			
			
			.anyRequest().permitAll()
		
			// 로그인 설정
			.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginOk")
			.defaultSuccessUrl("/")
			.failureHandler(new CustomLoginFailureHandler())
			.successHandler(new CustomLoginSuccessHandler("/layout/user/index"))
			
			// 예외처리 설정
			.and()  
			.exceptionHandling()
			.accessDeniedHandler(new CustomAccessDeniedHandler())
			
			// 로그아웃 설정
			.and()  
			.logout()
			.logoutUrl("/logout")  // 로그아웃 수행 url
			.logoutSuccessUrl("/") // 로그아웃 성공후 redirect url
			.invalidateHttpSession(true)   // sesssion invalidate 디폴트 true
			.logoutSuccessHandler(new CustomLogoutSuccessHandler())
			;
	}
}












