package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDTO {
    private String mid;
    private String mpw;
    private String email;
    private String name;
    private LocalDate birthday;
    private Gender gender;
    private String phone;
    private String address;
    private Float rating;
    private Long ratingCount;

    private boolean del;
    private boolean social;
}
