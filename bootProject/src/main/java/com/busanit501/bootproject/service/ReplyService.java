package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.PageRequestDTO;
import com.busanit501.bootproject.dto.PageResponseDTO;
import com.busanit501.bootproject.dto.ReplyDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    ReplyDTO readOne(Long rno);
    void update(ReplyDTO replyDTO);
    void delete(Long rno);
    // 부모 게시글 번호에 대한 댓글 목록 조회.
    PageResponseDTO<ReplyDTO> listWithReply(Long bno,PageRequestDTO pageRequestDTO);
}


