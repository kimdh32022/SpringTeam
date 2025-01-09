package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@Log4j2
@RequiredArgsConstructor
public class ScheduleController {

    private final CalendarService calendarService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<CalendarDTO>> getUserSchedules(@PathVariable("userId") Long userId) {
        List<CalendarDTO> schedules = calendarService.getUserSchedules(userId);

        log.info("Schedules: {}", schedules);

        schedules.forEach(calendarDTO -> {

            LocalDateTime localDateTime = calendarDTO.getWalkDate().atTime(calendarDTO.getWalkTime());
            String isoDate = localDateTime.toString();  // LocalDateTime은 자동으로 ISO 형식으로 변환

            calendarDTO.setWalkDateIso(isoDate);
        });

        return ResponseEntity.ok(schedules);
    }

}
