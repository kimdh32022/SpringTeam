package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.MemberJoinDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Test
    public void testRegisterMember() throws MemberService.MidExistException {
        // 더미 데이터 필요, 임시 DTO 생성.
        MemberJoinDTO memberJoinDTO = new MemberJoinDTO();
        memberJoinDTO.setMid("lsy");
        memberJoinDTO.setMpw("1234");
        memberJoinDTO.setEmail("lsy@naver.com");
        memberService.join(memberJoinDTO);

//        Long bno = boardService.register(boardDTO);
//        log.info("입력한 게시글 번호: " + bno.toString());
    }



}
