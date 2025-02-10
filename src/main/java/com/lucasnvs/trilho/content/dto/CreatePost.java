package com.lucasnvs.trilho.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePost {
    private String title;
    private String author;
    private String imageResource;
    private String content;
}
