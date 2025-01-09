package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {


    @Query("SELECT c FROM Calendar c WHERE c.user.userId = :userId")
    List<Calendar> findUserId(@Param("userId") Long userId);
}