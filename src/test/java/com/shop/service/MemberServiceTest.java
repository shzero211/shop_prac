package com.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
@Autowired
MemberService memberService;

@Autowired
PasswordEncoder passwordEncoder;

public Member createMember() {
	MemberFormDto memberFormDto=new MemberFormDto();
	memberFormDto.setEmail("test@email.com");
	memberFormDto.setName("홍길동");
	memberFormDto.setAddress("서울시 마포구");
	memberFormDto.setPassword("1234");
	return Member.createMember(memberFormDto, passwordEncoder);
}

@Test
@DisplayName("회원가입 테스트")
public void saveMemberTest() {
	Member member1=createMember();
	Member member2=createMember();
	memberService.saveMember(member1);
	Throwable e=assertThrows(IllegalStateException.class,()->{memberService.saveMember(member2);});
	assertEquals("이미 가입된 회원 입니다.",e.getMessage());
}

}
