package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "user/login"; // login.html로 이동
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        UserDTO user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user); // 세션에 사용자 정보 저장
            log.info("로그인 성공");
            return "redirect:/users/main"; // main.html로 리다이렉션
        }
        log.info("로그인 실패");
        redirectAttributes.addFlashAttribute("message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
        return "redirect:/users/login"; // 로그인 페이지로 리다이렉션
    }
    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupForm(Model model) {
        return "user/signup"; // signup.html로 이동
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@ModelAttribute UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/users/login"))
                .build();
        //리다이렉트로 데이터 탑재후 login페이지로 이동시키면 됌
    }

    // 이메일 중복 확인
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.checkEmailExists(email);
        return ResponseEntity.ok().body(Map.of("exists", exists));
    }

    // 사용자 생성 (Create)
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    // 사용자 조회 (Read)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // 모든 사용자 조회 (Read)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 사용자 업데이트 (Update)
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // 사용자 삭제 (Delete)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/main")
    public String mainPage(Model model) {
        return "/main"; // main.html로 이동
    }

    @GetMapping("/profile")
    public String profilePage(Model model) {
        return "/user/profile";
    }
}
