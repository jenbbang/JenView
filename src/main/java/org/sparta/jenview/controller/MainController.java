package org.sparta.jenview.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sparta.jenview.service.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/api")
public class MainController {
    private final JWTUtil jwtUtil;

    public MainController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String mainAPI() {

        return "메인 페이지 입니다.";
    }

//    @GetMapping("/login")
//    public String loginAPI() {
//
//        return "로그인 되었습니다.";
//    }
    @GetMapping("/login")
    public String loginAPI(HttpServletResponse response) {
        String token = jwtUtil.createJwt("user", "ROLE_USER");
        Cookie cookie = createCookie("JWT", token);
        response.addCookie(cookie);
        return "로그인 성공했습니다.";
    }


    @PostMapping("/logout")
    public String logoutAPI(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            System.out.println("User logged out successfully.");
        }

        // Authorization 쿠키 제거
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/"); // Ensure this path matches the path used when setting the cookie
        cookie.setHttpOnly(true); // Optional: If the cookie is HttpOnly, set this flag
        response.addCookie(cookie);

        System.out.println("Authorization cookie removed.");

        return "";
    }

    @GetMapping("/logoutsuccess")
    public String logoutsuccess() {
        return "로그아웃 되었습니다.";
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) (jwtUtil.getExpirationTime() / 1000)); // 20 minutes in seconds
        //cookie.setSecure(true); // Uncomment if you are using HTTPS
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}