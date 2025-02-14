package com.lucasnvs.trilho.auth.repositories;

import com.lucasnvs.trilho.auth.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Nullable String email);
}
