package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Pets;
import com.busanit501.bootproject.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pets, Long> {
    List<Pets> findByUsers(Users user); // users 필드를 기준으로 반려동물 조회
}
