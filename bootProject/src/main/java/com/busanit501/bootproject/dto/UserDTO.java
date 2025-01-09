package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long userId;           // 사용자 ID (Long으로 수정)
    private String email;          // 사용자 이메일
    private String password;       // 비밀번호 (필요시 암호화된 상태로 전달)
    private String name;           // 사용자 이름
    private LocalDate birth;       // 생년월일 (LocalDate로 수정)
    private String gender;         // 성별
    private String address;        // 주소
    private String profilePicture; // 프로필 사진 URL
    private String phoneNumber;    // 전화번호
    private float rating;          // 평균 평점
    private int ratingCount;       // 리뷰 수
}
