package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Member;
import com.busanit501.bootproject.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원 가입 테스트
    @Test
    public void testInsertMember() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@test.com")
                    .build();
            // 각 유저별로 권한 추가

            member.addRole(MemberRole.USER);

            if (i>=90) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

    @Test
    @Transactional
    public void testSelectMember() {
        Optional<Member> result = memberRepository.getWithRoles("member90");
        Member member = result.orElseThrow();
        log.info(member);
        log.info(member.getRoleSet());
        member.getRoleSet().forEach(role -> log.info(role.name()));

    }
    // 사용자 패스워드 변경해보기.
    @Test
    @Commit
    public void testUpdatePassword() {
        String mid = "lsy3709@kakao.com";
        String mpw = passwordEncoder.encode("1234");
        memberRepository.updatePassword(mid, mpw);
    }

}
