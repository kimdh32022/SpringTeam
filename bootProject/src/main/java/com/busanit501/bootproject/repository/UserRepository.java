package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // 추가적인 쿼리 메소드 정의 가능
    Users findByEmail(String email);
}
