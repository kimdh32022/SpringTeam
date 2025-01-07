package com.busanit501.bootproject.service;//package com.busanit501.bootproject.service;
//
//import com.busanit501.bootproject.dto.PageRequestDTO;
//import com.busanit501.bootproject.dto.PageResponseDTO;
//import com.busanit501.bootproject.dto.ReplyDTO;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//
//@SpringBootTest
//@Log4j2
//public class ReplyServiceTests {
//    @Autowired
//    private ReplyService replyService;
//
//    @Test
//    public void testRegisterReply() {
//        // 더미 데이터 필요, 임시 DTO 생성.
//        ReplyDTO replyDTO = ReplyDTO.builder()
//                .replyText("오늘 점심 뭐 먹지?")
//                .replyer("이상용")
//                .bno(122L)
//                .regDate(LocalDateTime.now())
//                .build();
//
//        // register 함수 호출 전에도,  bno 존재
//        log.info("replyDTO , register 함수 호출 하기전 : "+replyDTO);
//        Long bno = replyService.register(replyDTO);
//        log.info("입력한 댓글 번호: " + bno.toString());
//    }
//
//    @Test
//    public void testReadReply() {
//        ReplyDTO replyDTO = replyService.readOne(9L);
//        log.info("조회한 댓글 내용: " + replyDTO);
//    }
//
//    @Test
//    public void testUpdateReply() {
//        // 더미 데이터 필요, 임시 DTO 생성.
//        // 수정할 댓글 번호가 없어요. 더미로 추가함.
//        // 각자 디비에 따라서 조금씩 달라요.
//        //
//        ReplyDTO replyDTO = ReplyDTO.builder()
//                .replyText("수정 테스트 내용 변경만 확인해주세요.")
//                .replyer("이상용")
//                .rno(9L)
//                .bno(122L)
//                .regDate(LocalDateTime.now())
//                .build();
//
//        // register 함수 호출 전에도,  bno 존재
//        log.info("replyDTO , register 함수 호출 하기전 : "+replyDTO);
//        replyService.update(replyDTO);
//
//    }
//
//    @Test
//    public void testDeleteReply() {
//        replyService.delete(9L);
//    }
//
//    @Test
//    public void testAllReply() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .build();
//        PageResponseDTO<ReplyDTO> result = replyService.listWithReply(121L,pageRequestDTO);
//       log.info("result : " + result);
//    }
//}
