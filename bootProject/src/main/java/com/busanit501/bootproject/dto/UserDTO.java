package com.busanit501.bootproject.dto;

import com.busanit501.bootproject.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private long userId;
    private String email;
    private String name;
    private Integer age;
    private Gender gender;
    private String address;
    private String profilePicture;
    private String phoneNumber;
    private boolean isVerified;
    private Timestamp createdAt;
}
