package com.lec.foodmarket.config;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.service.MemberService;

@Service
public class MemberDetailsService implements UserDetailsService {

	@Autowired
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberService.findById(username);   // DB 조회
		// 해당 username 의 user 가 있다면
		// UserDetails 생성하여 리턴
		if(member != null) {
			MemberDetails memberDetails = new MemberDetails(member);
			memberDetails.setMemberService(memberService);
			return memberDetails;
		}
		
		// 해당 username 의 user 가 없다면!
		// UsernameNotFoundException을 throw 해주도록 한다
		throw new UsernameNotFoundException(username);
		
	}

}


















