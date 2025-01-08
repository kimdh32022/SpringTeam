package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.ChatingRoom;
import com.busanit501.bootproject.dto.ChatingRoomDTO;
import com.busanit501.bootproject.dto.MessageDTO;
import com.busanit501.bootproject.dto.ChatRoomRegisterDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.ChatingRoomService;
import com.busanit501.bootproject.service.MessageService;
import com.busanit501.bootproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@Log4j2
@RequestMapping("/chatingRoom")
@RequiredArgsConstructor
public class ChatingRoomController {
    private final ChatingRoomService chatingRoomService;
    private final MessageService messageService;
    private final UserService userService;
    // 채팅방 목록 조회
    @GetMapping("/roomList")
    public void roomList(@RequestParam(required = false, defaultValue = "") String keyword
            , UserDTO userDTO
            , Model model) {
        long userId = 1; // 현재 유저 ID (테스트용)
        List<ChatingRoomDTO> roomList = chatingRoomService.searchAllChatingRoom(keyword, userId);
        model.addAttribute("roomList", roomList);
        model.addAttribute("keyword", keyword);
    }
    //채팅방 목록 조회
    @ResponseBody
    @PostMapping("/roomRegister")
    public void registerRoom(@RequestBody ChatRoomRegisterDTO chatRoomRegisterDTO) {
        chatingRoomService.addChatingRoom(chatRoomRegisterDTO.getChatingRoomDTO(),
                chatRoomRegisterDTO.getChatRoomParticipantsDTO());
    }
    //매칭방 나가기
    @ResponseBody
    @PostMapping("/roomUAD")
    public Map<String,Long> roomUAD(@RequestBody ChatRoomRegisterDTO chatRoomRegisterDTO){
        log.info("RoomRegisterDTO: " + chatRoomRegisterDTO);
        chatingRoomService.exitChatingRoom(chatRoomRegisterDTO.getChatingRoomDTO());
        chatingRoomService.deleteRoomParticipants(chatRoomRegisterDTO.getChatRoomParticipantsDTO().getChatRoomId(),
                chatRoomRegisterDTO.getChatRoomParticipantsDTO().getSenderId());
        messageService.deleteAllMessagesByUser(chatRoomRegisterDTO.getChatRoomParticipantsDTO().getSenderId(),
                chatRoomRegisterDTO.getChatRoomParticipantsDTO().getChatRoomId());
        Map<String, Long> map = Map.of("UserId",chatRoomRegisterDTO.getChatRoomParticipantsDTO().getSenderId());
        return map;
    }
    //채팅방 수정
    @ResponseBody
    @PutMapping("/{roomId}")
    public Map<String, Long> updateRoom(@RequestBody ChatingRoomDTO roomDTO,
                                           @PathVariable("roomId") long roomId) {
        chatingRoomService.updateChatingRoom(roomDTO);
        Map<String, Long> map = Map.of("roomId",roomId);
        return map;
    }
    // 채팅방 삭제
    @ResponseBody
    @DeleteMapping(value = "/{roomId}")
    public Map<String, Long> deleteRoom(@PathVariable("roomId") long roomId) {
        chatingRoomService.deleteChatingRoom(roomId);
        Map<String, Long> map = Map.of("roomId",roomId);
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

    //메세지 작성
    @ResponseBody
    @PostMapping("/messageRegister")
    public Map<String, Long> registerMessage(@RequestBody MessageDTO messageDTO) {
        long messageId = messageService.addMessage(messageDTO);
        Map<String,Long> map = Map.of("messageId",messageId);
        log.info("map : " + map);
        return map;
    }
    //메세지 삭제(본인꺼)
    @ResponseBody
    @DeleteMapping(value = "/messageDelete/{messageId}")
    public Map<String, Long> deleteMessage(@PathVariable("messageId") long messageId) {
        messageService.deleteMessage(messageId);
        Map<String, Long> map = Map.of("messageId",messageId);
        //log.info("map : " + map);
        return map;
    }
    //유저 조회
    @ResponseBody
    @GetMapping("/userList/{roomId}")
    public List<UserDTO> getUserList(@RequestParam(required = false, defaultValue = "") String keyword,
                                     @PathVariable("roomId") long roomId){
        log.info("키워드3 : " + keyword);
        log.info("roomId : " + roomId);
        List<UserDTO> list = userService.searchInviteUser(keyword, roomId);
        log.info("초대가능한 유저 리스트 : " + list);
        return list;
    }
    //채팅방에 유저 초대
    @ResponseBody
    @PostMapping("/invite")
    public List<Map<String, Long>> inviteUsers(@RequestBody List<ChatRoomRegisterDTO> chatRoomRegisterDTOList) {
        List<Map<String, Long>> responseList = new ArrayList<>();

        for (ChatRoomRegisterDTO chatRoomRegisterDTO : chatRoomRegisterDTOList) {
            log.info("Processing ChatRoomRegisterDTO: " + chatRoomRegisterDTO);

            // 초대 처리: 채팅방에 유저를 초대하는 서비스 호출
            chatingRoomService.inviteChatingRoom(
                    chatRoomRegisterDTO.getChatingRoomDTO(),
                    chatRoomRegisterDTO.getChatRoomParticipantsDTO()
            );

            // 처리 결과 저장
            Map<String, Long> result = Map.of("UserId", chatRoomRegisterDTO.getChatRoomParticipantsDTO().getSenderId());
            responseList.add(result);
        }

        return responseList;
    }
}
