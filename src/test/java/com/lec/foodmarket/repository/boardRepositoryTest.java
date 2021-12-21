package com.lec.foodmarket.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lec.foodmarket.domain.Member;
import com.lec.foodmarket.domain.Notice;

@SpringBootTest
public class boardRepositoryTest {
	
	@Autowired
	private NoticeRepository noticeRepository; // 공지사항
	@Autowired
	private MemberRepository memberRepository;
	
	@Test
	void crud() {
		System.out.println("\n--TEST#crud()------------------------------------------------");
		Member member = Member.builder()
				.id("admin")
				.addr("모충동")
				.detailAddr("105호")
				.name("운영자")
				.originProfile("안녕")
				.phoneNo("010-5103-1570")
				.pw("admin")
				.recommender("안녕")
				.saveProfile("안녕")
				.saveUpPoint(12)
				.build();
		
		memberRepository.save(member);
		
		Notice notice = Notice.builder()
				.content("안녕하세요")
				.title("제목입니다")
				.id(member)
				.build();
				
				
				
		noticeRepository.save(notice);
		noticeRepository.findAll().forEach(System.out::println);
		
		
		
		
		System.out.println("---------------------------------------------------------------");
	}
	
}
