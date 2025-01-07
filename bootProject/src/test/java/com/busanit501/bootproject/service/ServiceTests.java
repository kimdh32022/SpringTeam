package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Log4j2
@SpringBootTest
public class ServiceTests {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegisterBoard() {
        // 더미 데이터 필요, 임시 DTO 생성.
        BoardDTO boardDTO = BoardDTO.builder()
                .title("오늘 점심 뭐 먹지?")
                .content("라면 먹어야지")
                .writer("이상용")
                .regDate(LocalDateTime.now())
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("입력한 게시글 번호: " + bno.toString());
    }

    @Test
    public void testSelectOneBoard() {
        // 더미 데이터 필요, 임시 DTO 생성.
        Long bno = 103L;
        BoardDTO boardDTO= boardService.readOne(bno);
        log.info("testSelectOneBoard , 하나 조회 boardDTO: " + boardDTO.toString());
    }

    @Test
    public void testUpdateBoard() {
        // 수정할 더미 데이터 필요,
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(103L)
                .title("수정한 오늘 점심 뭐 먹지?")
                .content("칼국수 먹을까?")
                .build();
        boardService.update(boardDTO);

    }

    @Test
    public void testDeleteBoard() {
        boardService.delete(103L);
    }

    @Test
    public void testSelectAllBoard() {
        // 검색할 더미 데이터
        // 준비물 1) PageRequestDTO, 키워드, 페이지, 사이즈 정보가 다 있음.
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .type("tcw")
                        .keyword("샘플")
                        .size(10)
                        .build();

        PageResponseDTO<BoardDTO> list = boardService.list(pageRequestDTO);
        log.info("list: " + list.toString());
    }

    @Test
    @Transactional
    public void testSelectAllBoardWithReplyCount() {
        // 검색할 더미 데이터
        // 준비물 1) PageRequestDTO, 키워드, 페이지, 사이즈 정보가 다 있음.
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .type("tcw")
                        .keyword("샘플")
                        .size(10)
                        .build();

        PageResponseDTO<BoardListReplyCountDTO> list = boardService.listWithReplyCount(pageRequestDTO);
        log.info("list: " + list.toString());
    }

    @Test
    public void testRegisterBoardWithImage() {
        // 더미 게시글
        BoardDTO boardDTO = BoardDTO.builder()
                .title("첨부 이미지 추가 더미 게시글")
                .content("첨부 이미지 추가 더미 게시글 내용")
                .writer("이상용첨부이미지작업중")
                .build();

//         더미 파일 이름들
        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aa.png",
                        UUID.randomUUID()+"_bb.png",
                        UUID.randomUUID()+"_cc.png"
                )
        );
        Long bno = boardService.register(boardDTO);
        log.info("bno: " + bno);


    }

    // 상세보기, 조회 기능 단위 테스트
    @Test
    public void testReadWithImage() {

        BoardDTO boardDTO = boardService.readOne(2L);
        log.info("testReadWithImage, 하나 조회 boardDTO : " + boardDTO);
        for(String fileImage : boardDTO.getFileNames()){
            log.info("각 이미지 파일명만 조회 : " + fileImage);
        }

    }

    // 수정, 첨부 이미지를 수정 할 경우,

    @Test
    public void testUpdateWithImages() {
        // 변경시, 변경할 더미 데이터, 임시, 601L
// 화면에서 넘어온 더미 데이터 만들기. DTO 타입.
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 : 수정버전")
                .content("내용 : 수정버전")
                .build();

        // 더미 데이터에 첨부 이미지 파일 추가.
        // 경우의수,
        // 기존의 첨부 이미지들을 모두 지우고, 새로운 첨부 이미지를 추가.
        // 1) 기존 첨부이미지 3장, 모두 교체할 경우.
        // 예시)1.jpg,2.jpg,3.jpg -> 4.jpg, 5.jpg

        // 2) 기존 첨부이미지 3장, 2장 삭제, 1장 교체할 경우.
        // 예시)1.jpg(유지),2.jpg(삭제),3.jpg(삭제)
        //  4.jpg(추가), 5.jpg(추가) -> 1.jpg(유지), 4.jpg(추가), 5.jpg(추가)
        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_sampleImage.png",
                        UUID.randomUUID()+"_sampleImage2.png"
                )
        );

        //디비에서 조회하기.
        boardService.update(boardDTO);
    }

    // 삭제 테스트 1) 댓글이 있는 경우, 2) 댓글 없는 경우
    @Test
    public void testDeleteBoardReplyWithImage() {
        Long bno = 2L;
        boardService.delete(bno);
    }

    // 모두조회, 게시글 + 댓글갯수 + 첨부 이미지들
    @Test
    @Transactional
    public void testSelectAllBoardWithReplyCountAndImage() {
        // 검색할 더미 데이터
        // 준비물 1) PageRequestDTO, 키워드, 페이지, 사이즈 정보가 다 있음.
        PageRequestDTO pageRequestDTO =
                PageRequestDTO.builder()
                        .page(1)
                        .type("tcw")
                        .keyword("ㅇㅇ")
                        .size(10)
                        .build();

        PageResponseDTO<BoardListAllDTO> list = boardService.listWithAll(pageRequestDTO);
        log.info("list: " + list.toString());
    }

}
