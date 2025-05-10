package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import com.example.backend.dto.LoginRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Collections;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")


public class UserController {

    private final UserRepository userRepository;
    private final UserService userService; // ✅ 주입할 서비스

    // ✅ 생성자 주입
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // 기존 API
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // ✅ 회원가입 (POST /register)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("아이디와 비밀번호는 필수입니다.");
        }

        if (userService.isUsernameTaken(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }

        User saved = userService.registerUser(user);
        return ResponseEntity.ok(saved);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok("로그인 성공! 사용자 ID: " + user.getId());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }
    @GetMapping("/check/{username}")
    public Map<String, Boolean> checkUsername(@PathVariable String username){
            boolean taken = userRepository.findByUsername(username) != null;
            return Collections.singletonMap("available", !taken);
    }
}
