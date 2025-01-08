package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepostiory extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.* " +
            "FROM user u " +
            "WHERE u.user_id NOT IN (SELECT rp.sender_id " +
            "FROM room_participants rp " +
            "WHERE rp.chat_room_id = :roomId) " +
            "AND u.name like concat('%', :keyword, '%') "
            ,nativeQuery = true)
    List<User> searchInviteUserList(String keyword, long roomId);
}
