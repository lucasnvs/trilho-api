package com.lucasnvs.trilho.content.services;

import com.lucasnvs.trilho.content.domain.Post;
import com.lucasnvs.trilho.content.dto.CreatePost;
import com.lucasnvs.trilho.content.dto.ResponsePost;
import com.lucasnvs.trilho.content.repositories.PostRepository;
import com.lucasnvs.trilho.shared.services.FileStorageService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FileStorageService fileStorageService;

    public List<ResponsePost> getAllPosts() {
        List<Post> products = postRepository.findAll();
        return products.stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ResponsePost getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return postMapper.toResponse(post);
    }

    public ResponsePost savePost(CreatePost createPost) {
        val image = createPost.getImageResource();
        Post post = postMapper.toEntity(createPost);
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile("posts", image);
            post.setImageResource(fileName);
        }
        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    public boolean deletePost(Long id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new RuntimeException("Post with ID " + id + " not found");
        }
        return false;
    }
}
