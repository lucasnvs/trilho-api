package com.lucasnvs.trilho.auth.dto;


import com.lucasnvs.trilho.auth.domain.user.User;

public record UserResponseDTO(String username, String email) {

    public UserResponseDTO(User user) {
        this(user.getName(), user.getEmail());
    }
}