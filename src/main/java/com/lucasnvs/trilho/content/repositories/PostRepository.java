package com.lucasnvs.trilho.content.repositories;

import com.lucasnvs.trilho.content.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> { }