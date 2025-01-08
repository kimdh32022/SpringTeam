package com.busanit501.bootproject.repository;

import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.domain.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface CalendarRepository extends JpaRepository<Calendar, Long> {

//
//    @Query("select b from Calendar b where b.schedulename like concat('%',:keyword,'%')")
//    Page<Calendar> findByKeyword(String keyword, Pageable pageable);
//
//    @Query(value = "select now()" , nativeQuery = true)
//    String now();
//

    // @Query를 사용하여 날짜가 오늘 이전이고, 일정 상태가 '예정(SCHEDULED)'인 일정들을 조회
    @Query("SELECT c FROM Calendar c WHERE c.walkDate < :walkDate AND c.status = :status")
    List<Calendar> findByWalkDate(@Param("walkDate") LocalDate walkDate,
                                  @Param("status") ScheduleStatus status);

    @Query("SELECT c FROM Calendar c WHERE c.user.id = :userId")
    List<Calendar> findByUserId(@Param("userId") Long userId);}
