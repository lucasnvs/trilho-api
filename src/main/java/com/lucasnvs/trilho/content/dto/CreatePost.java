package com.lucasnvs.trilho.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePost {
    private String title;
    private String author;
    private MultipartFile imageResource;
    private String content;
}
