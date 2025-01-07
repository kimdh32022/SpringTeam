package com.busanit501.bootproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pets extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId; // 반려동물 ID

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;  // 사용자 ID (FK)

    @Column(length = 100, nullable = false)
    private String name; // 반려동물 이름

    @Column(length = 50, nullable = false)
    private String type; // 반려동물 종류

    @Column(nullable = false)
    private LocalDate birth; // 생년월일

    @Column(nullable = false)
    private String gender; // 성별 (ENUM 가능)

    @Column(columnDefinition = "TEXT")
    private String personality; // 성격

    @Column(nullable = false)
    private float weight; // 몸무게

    @Column(length = 255, nullable = true)
    private String profilePicture; // 프로필 사진 URL

    @Column(nullable = false)
    private boolean isVerified; // 인증 여부

    public void setUsers(Users user) {
        this.users = user; // users 필드 설정
    }
}

