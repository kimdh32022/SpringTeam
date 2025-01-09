package com.busanit501.bootproject.controller;


import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/calendar")
@Log4j2
@RequiredArgsConstructor
public class CalendarController {

        private final CalendarService calendarService;

        @GetMapping

        public String calender(Model model, UserDTO user) {
                List<CalendarDTO> schedules = calendarService.getUserSchedules(user.getUserId());
                model.addAttribute("schedules", schedules);
                return "calendar";

        }
}






