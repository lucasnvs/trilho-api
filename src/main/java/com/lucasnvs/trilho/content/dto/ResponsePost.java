package com.lucasnvs.trilho.content.dto;

import com.lucasnvs.trilho.content.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePost {
    private Long id;
    private String title;
    private String author;
    private String imageResource;
    private String content;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
