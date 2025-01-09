package com.busanit501.bootproject.controller;

import com.busanit501.bootproject.domain.Pets;
import com.busanit501.bootproject.dto.PetDTO;
import com.busanit501.bootproject.dto.UserDTO;
import com.busanit501.bootproject.service.PetService;
import com.busanit501.bootproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private PetService petService;

    // 로그인 화면 호출
    @GetMapping("/login")
    public void loginGet(String error, String logout,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        log.info("loginGet===================");
        log.info("logoutTest ===================" + logout);

        if (logout != null) {
            log.info("logoutTest user ====================");
        }
        if (error != null) {
            // 403 , error 만 확인한 상태
            log.info("logoutTest error ====================" + error);
//            redirectAttributes.addFlashAttribute(
//                    "error", error);
            model.addAttribute("error", error);
        }

    }

    // 로그인 화면에서 기믹 수행
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("users", user); // 세션에 사용자 정보 저장
            log.info("로그인 성공");
            return "redirect:/users/profile?userId=" + user.getUserId(); // main.html로 리다이렉션
        }
        log.info("로그인 실패");
        redirectAttributes.addFlashAttribute("message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
        return "redirect:/user/login"; // 로그인 페이지로 리다이렉션
    }

    // 회원가입 화면 호출
    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "user/signup"; // signup.html로 이동
    }

    // 회원가입 화면에서 기믹 수행
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@ModelAttribute UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/user/login"))
                .build();
        //리다이렉트로 데이터 탑재후 login페이지로 이동시키면 됌
    }

    // 프로필 화면 호출 및 데이터 탑재
    @GetMapping("/profile")
    public String profilePage(@RequestParam Long userId, Model model) {
        UserDTO user = userService.getUserById(userId); // DB에서 최신 데이터 가져오기
        model.addAttribute("users", user);
        model.addAttribute("pets", petService.getAllPets());
        return "/user/profile"; // 프로필 페이지 템플릿
    }

    // 수정 창 호출
    @GetMapping("/update")
    public String updatePage(@RequestParam Long userId, Model model) {
        UserDTO userDTO = userService.getUserById(userId);
        model.addAttribute("users", userDTO);
        return "user/update";
    }

    // 수정 로직 사용
    @PostMapping("/update")
    public String update(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        userService.updateUser(userDTO.getUserId(), userDTO);
        session.setAttribute("users", userDTO);
        redirectAttributes.addFlashAttribute("message", "회원정보를 성공적으로 수정했습니다.");
        return "redirect:/user/profile?userId=" + userDTO.getUserId();
    }

    // 삭제 로직 처리
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long userId, HttpSession session, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId); // 사용자 삭제 서비스 호출

        // 세션에서 사용자 정보 제거
        session.invalidate(); // 또는 session.removeAttribute("users");

        redirectAttributes.addFlashAttribute("message", "회원탈퇴가 완료되었습니다.");
        return "redirect:/user/login"; // 홈 페이지 등으로 리다이렉트
    }

    //이메일 중복 기믹 수행
    @PostMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userService.checkEmailExists(email);
        return ResponseEntity.ok().body(Map.of("exists", exists));
    }

    // 펫등록페이지 호출
    @GetMapping("/pet/register")
    public String registerPet() {
        return "pet/register"; // templates/pet/register.html로 이동
    }

    @PostMapping("/pet/register")
    public String registerPet(@ModelAttribute PetDTO petDTO, RedirectAttributes redirectAttributes) {
        petService.registerPet(petDTO);
        session.setAttribute("pets", petDTO);
        return "redirect:/users/profile?userId=" + petDTO.getUserId();
    }

    @GetMapping("/users/pets")
    public ResponseEntity<List<Pets>> getPetsForUser(@RequestParam Long userId) {
        List<Pets> pets = petService.getPetsByUserId(userId); // 유저 ID에 해당하는 강아지 목록을 가져옵니다.
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        return "/main"; // main.html로 이동
    }

}
