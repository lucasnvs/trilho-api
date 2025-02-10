package com.lucasnvs.trilho.content.controller;

import com.lucasnvs.trilho.content.domain.Post;
import com.lucasnvs.trilho.content.dto.CreatePost;
import com.lucasnvs.trilho.content.dto.ResponsePost;
import com.lucasnvs.trilho.content.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<ResponsePost>> getAllPosts() {
        List<ResponsePost> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePost> getPostById(@PathVariable Long id) {
        ResponsePost post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ResponsePost> createPost(@RequestBody CreatePost createPost) {
        ResponsePost newInventory = postService.savePost(createPost);
        return ResponseEntity.status(201).body(newInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}