package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.service.CalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
//@RequestMapping("/calender")
@RequiredArgsConstructor
public class CalendarController {

        private final CalendarService calendarService;

        @GetMapping("/calendar")
        public String calendar(Model model) {
                List<CalendarDTO> calendars = calendarService.getAllCalendars();
                model.addAttribute("calendars", calendars);
                return "calendar";
        }





}
