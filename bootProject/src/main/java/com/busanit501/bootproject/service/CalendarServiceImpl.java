package com.busanit501.bootproject.service;


import com.busanit501.bootproject.domain.ScheduleStatus;
import com.busanit501.bootproject.dto.CalendarDTO;
import com.busanit501.bootproject.domain.Calendar;
import com.busanit501.bootproject.repository.CalendarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarrepository;

    @Override
    public Long register(CalendarDTO calendarDTO) {
        Calendar calendar = dtoToEntity(calendarDTO);
        Long id = calendarrepository.save(calendar).getScheduleId();
        return id;
    }

    @Override
    public CalendarDTO readOne(Long scheduleId) {
        Optional<Calendar> result = calendarrepository.findById(scheduleId);
        Calendar calendar= result.orElseThrow();
        CalendarDTO dto = entityToDto(calendar);
        return dto;
    }

    @Override
    public void update(CalendarDTO calendarDTO) {

        Optional<Calendar> result = calendarrepository.findById(calendarDTO.getScheduleId());
        Calendar calendar = result.orElseThrow();
        calendar.changeCalendar(calendarDTO.getSchedulename(),
                calendarDTO.getWalkDate(), calendarDTO.getWalkTime());

        calendarrepository.save(calendar);
    }

    @Override
    public void delete(Long scheduleId) {
        calendarrepository.deleteById(scheduleId);
    }

    @Override
    public List<CalendarDTO> getAllCalendars() {
        List<Calendar> calendar = calendarrepository.findAll();

        List<CalendarDTO> calendarDTO = calendar.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());

        return calendarDTO;

    }

    // 일정 상태를 자동으로 '완료'로 변경하는 스케줄링 메서드
    @Override
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateScheduledEvents() {
        LocalDate today = LocalDate.now(); // 오늘 날짜
        LocalTime now = LocalTime.now(); // 현재 시간

        // 오늘 날짜 이전의 일정들을 '완료' 상태로 변경
        List<Calendar> events = calendarrepository.findByWalkDate(today, ScheduleStatus.SCHEDULED);

        for (Calendar event : events) {
            // 예정된 시간보다 현재 시간이 지나면 완료 처리
            if (event.getWalkTime().isBefore(now)) {
                event.changeScheduleStatus(event.getSchedulename(), event.getWalkDate(), event.getWalkTime(), ScheduleStatus.COMPLETED);
            }
        }

        calendarrepository.saveAll(events); // 변경된 일정을 저장
        log.info("일정 상태 업데이트 완료: " + events.size() + "개의 일정이 완료 상태로 변경되었습니다.");
    }

//
//
//    @Override
//    public PageResponseDTO<CalendarDTO> list(PageRequestDTO pageRequestDTO) {
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("bno");
//
//        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);
//        // list -> PageResponseDTO 타입으로 변경 필요.
//
//        // result.getContent() -> 페이징된 엔티티 클래스 목록
//        List<BoardDTO> dtoList = result.getContent().stream()
//                .map(board ->modelMapper.map(board, BoardDTO.class))
//                .collect(Collectors.toList());
//
//
//        return PageResponseDTO.<BoardDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int) result.getTotalElements())
//                .build();
//
//}     // list
//
//
//    @Override
//    public PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
//
//
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("bno");
//
//        // 수정1
//        Page<BoardListAllDTO> result = boardRepository.searchWithAll(types,keyword,pageable);
//
//        return PageResponseDTO.<BoardListAllDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(result.getContent())
//                .total((int) result.getTotalElements())
//                .build();
//    }
}
