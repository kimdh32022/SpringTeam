package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostiory extends JpaRepository<User, Long> {
}
