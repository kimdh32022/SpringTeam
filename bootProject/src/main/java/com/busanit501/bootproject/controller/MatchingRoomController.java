package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.dto.MatchingRoomDTO;
import com.busanit501.bootproject.dto.MessageDTO;
import com.busanit501.bootproject.dto.RoomRegisterDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.MatchingRoomService;
import com.busanit501.bootproject.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@Controller
@Log4j2
@RequestMapping("/matchingRoom")
@RequiredArgsConstructor
public class MatchingRoomController {
    private final MatchingRoomService matchingRoomService;
    private final MessageService messageService;
    // 매칭룸 리스트 조회
    @GetMapping("/roomList")
    public void roomList(@RequestParam(required = false, defaultValue = "") String keyword
            , UserDTO userDTO
            , Model model) {
        long userId = 1; // 현재 유저 ID (테스트용)
        List<MatchingRoomDTO> roomList = matchingRoomService.searchAllMatchingRoom(keyword, userId);
        model.addAttribute("roomList", roomList);
        model.addAttribute("keyword", keyword);
    }
    //매칭방 추가
    @ResponseBody
    @PostMapping("/roomRegister")
    public void registerRoom(@RequestBody RoomRegisterDTO roomRegisterDTO) {
        matchingRoomService.addMatchingRoom(roomRegisterDTO.getMatchingRoomDTO(), roomRegisterDTO.getRoomParticipantsDTO());
        // 채팅방 목록을 다시 조회하여 모델에 추가
//        List<MatchingRoomDTO> roomList = matchingRoomService.searchAllMatchingRoom("", 1); // 1은 현재 유저 ID (테스트용)
//
//        // 모델에 방 목록과 메시지를 추가
//        redirectAttributes.addFlashAttribute("roomList", roomList);
//        redirectAttributes.addFlashAttribute("message", "채팅방이 성공적으로 생성되었습니다.");

        // 리다이렉트(작동안댐)
        //return "redirect:/matchingRoom/roomList";
    }
    //매칭방 나가기
    @ResponseBody
    @PostMapping("/roomUAD")
    public Map<String,Long> roomUAD(@RequestBody RoomRegisterDTO roomRegisterDTO){
        log.info("RoomRegisterDTO: " + roomRegisterDTO);
        matchingRoomService.exitMatchingRoom(roomRegisterDTO.getMatchingRoomDTO());
        matchingRoomService.deleteRoomParticipants(roomRegisterDTO.getRoomParticipantsDTO().getChatRoomId(),
                roomRegisterDTO.getRoomParticipantsDTO().getSenderId());
        Map<String, Long> map = Map.of("UserId",roomRegisterDTO.getRoomParticipantsDTO().getSenderId());
        return map;
    }
    //매칭방 수정
    @ResponseBody
    @PutMapping("/{roomId}")
    public Map<String, Long> updateRoom(@RequestBody MatchingRoomDTO roomDTO,
                                           @PathVariable("roomId") long roomId) {
        matchingRoomService.updateMatchingRoom(roomDTO);
        Map<String, Long> map = Map.of("roomId",roomId);
        return map;
    }

    // 채팅방 삭제
    @ResponseBody
    @DeleteMapping(value = "/{roomId}")
    public Map<String, Long> deleteRoom(@PathVariable("roomId") long roomId) {
        matchingRoomService.deleteMatchingRoom(roomId);
        Map<String, Long> map = Map.of("roomId",roomId);
        //log.info("map : " + map);
        return map;
    }

    //메세지 삭제(본인꺼)
    @ResponseBody
    @DeleteMapping(value = "/{messageId}")
    public Map<String, Long> deleteMessage(@PathVariable("messageId") long messageId) {
        messageService.deleteMessage(messageId);
        Map<String, Long> map = Map.of("messageId",messageId);
        //log.info("map : " + map);
        return map;
    }

    //채팅 조회
    @ResponseBody
    @GetMapping("/chatList/{roomId}")
    public List<MessageDTO> getChatList(@PathVariable("roomId") long roomId){
        List<MessageDTO> list = messageService.searchMessage(roomId);
        log.info("list : " + list);
        return list;
    }

    //채팅 추가
    @ResponseBody
    @PostMapping("/messageRegister")
    public Map<String, Long> registerMessage(@RequestBody MessageDTO messageDTO) {
        long messageId = messageService.addMessage(messageDTO);
        Map<String,Long> map = Map.of("messageId",messageId);
        log.info("map : " + map);
        return map;
    }
}
