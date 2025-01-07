package com.busanit501.bootproject.service;

import com.busanit501.bootproject.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception {

    }
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
    void update(MemberJoinDTO memberJoinDTO);

}
