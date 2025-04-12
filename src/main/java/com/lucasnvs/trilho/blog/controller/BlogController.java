package com.lucasnvs.trilho.blog.controller;

import com.lucasnvs.trilho.shared.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.ENDPOINT_BLOG)
public class BlogController {

    private static final Path BLOG_FOLDER = Paths.get("../blog-posts");

    @GetMapping("/post")
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("All ok");
    }

//    @GetMapping("/post")
//    public List<String> listPosts() throws IOException {
//        if (!Files.exists(BLOG_FOLDER)) {
//            return List.of();
//        }
//        return Files.list(BLOG_FOLDER)
//                .filter(Files::isRegularFile)
//                .map(path -> path.getFileName().toString())
//                .collect(Collectors.toList());
//    }

    @GetMapping("/post/{filename}")
    public String getPostContent(@PathVariable String filename) throws IOException {
        Path filePath = BLOG_FOLDER.resolve(filename).normalize();
        if (!filePath.startsWith(BLOG_FOLDER) || !Files.exists(filePath)) {
            throw new NoSuchFileException("Post not found or access denied");
        }
        return Files.readString(filePath);
    }
}
