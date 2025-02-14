package com.lucasnvs.trilho.auth.controllers;

import com.lucasnvs.trilho.auth.domain.user.User;
import com.lucasnvs.trilho.auth.dto.UserResponseDTO;
import com.lucasnvs.trilho.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUser() {
        List<User> users = this.repository.findAll();

        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userResponseDTOs);
    }
}
