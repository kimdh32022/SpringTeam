package com.busanit501.bootproject.security.dto;

import com.busanit501.bootproject.domain.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

// 여기 DTO, 리턴 타입을 UserDetails 반환 해야함.
// Security, 로그인 처리 후, 반환 타입을 약속함.
// Security 에서 제공하는 User 상속 받으면, 가장 간단하게 구현 가능함.

@Getter
@Setter
@ToString
// MemberSecurityDTO , 클래스는
// 다형성으로, 반환 타입, UserDetails ,되고,
// 반환 타입 , OAuth2User 됨.
public class MemberSecurityDTO extends User implements OAuth2User {

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

    // 소셜 로그인 정보를 담을 요소
    private Map<String, Object> props;

    public MemberSecurityDTO(String username, String password, String email,
                             boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;

    }

    public MemberSecurityDTO(String mid, String password, String email,
                             String name, LocalDate birthday, Gender gender, String phone, String address,
                             boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(mid, password, authorities);
        this.mid = mid;
        this.mpw = password;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.del = del;
        this.social = social;

    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.mid;
    }
}

