package com.busanit501.bootproject.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {
    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
}
