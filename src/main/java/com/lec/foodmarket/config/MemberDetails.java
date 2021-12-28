package com.lec.foodmarket.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.service.MemberService;

public class MemberDetails implements UserDetails {

	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	private Member member;
	public MemberDetails(Member member) {
		this.member = member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collect = new ArrayList<>();
		
		List<String> list = memberService.selectRoleById(member.getId());
		
		list.forEach(auth -> {
			collect.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return auth;
				}
				 
				@Override
				public String toString() {
					return auth;
				}
			});
		});
		
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPw();
	}

	@Override
	public String getUsername() {
		return member.getId();
	}

	// 계정이 만료 되었는지 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠긴건 아닌지?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정 credential 이 만료되었는지?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화 되었는지
	@Override
	public boolean isEnabled() {
		return true;
	}

	public Member getUser() {
		return member;
	}

}








