package org.sparta.jenview.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sparta.jenview.dto.UserDTO;
import org.sparta.jenview.service.JWTUtil;
import org.sparta.jenview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final JWTUtil jwtUtil;
    private final UserService userService;

    public UserController(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @GetMapping("/")
    public String mainAPI() {

        return "메인 페이지 입니다.";
    }

    @GetMapping("/login")
    public String loginAPI(HttpServletResponse response) {

        return "로그인 성공했습니다.";
    }

    @PostMapping("/logout")
    public String logoutAPI(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            System.out.println("User logged out successfully.");
        }
        return "";
    }

    @GetMapping("/userinfo")
    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> userDTO = userService.getUserList(); // <List<UserDTO>>
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/userinfo/{id}")
    public ResponseEntity<String> getUserInfo(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserInfo(id);
        return ResponseEntity.ok(userDTO.toString());
    }

    @GetMapping("/logoutsuccess")
    public String logoutsuccess() {
        return "로그아웃 되었습니다.";
    }

}