package com.firstsnow.blockchef.controller;

import com.firstsnow.blockchef.dto.signup.SignupRequest;
import com.firstsnow.blockchef.dto.signup.SignupResponse;
import com.firstsnow.blockchef.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 API
     * POST /api/users/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }


}