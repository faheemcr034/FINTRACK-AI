package Myproject.FINTRACK.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Myproject.FINTRACK.DTO.RegisterDTO;
import Myproject.FINTRACK.DTO.UserDTO;
import Myproject.FINTRACK.service.UserService;
import jakarta.persistence.Entity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterDTO registerDTO){
        UserDTO registeredUser = userService.registerUser(registerDTO);
        return ResponseEntity.status(201).body(registeredUser);
    }

}
