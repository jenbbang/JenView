package org.sparta.jenview.controller;

import org.sparta.jenview.dto.UserDto;
import org.sparta.jenview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // UserController 생성자, UserService를 주입받아 초기화
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // 새로운 사용자를 생성
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    // 모든 사용자 정보를 반환
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // 특정 ID를 가진 사용자 정보를 반환
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
        UserDto userDto = new UserDto(userService.getUser(id));
        return ResponseEntity.ok(userDto);
    }



    // 특정 ID를 가진 사용자 정보를 업데이트
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    // 특정 ID를 가진 사용자 정보를 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
