package com.lucasnvs.trilho.content.services;

import com.lucasnvs.trilho.content.domain.Post;
import com.lucasnvs.trilho.content.dto.CreatePost;
import com.lucasnvs.trilho.content.dto.ResponsePost;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public ResponsePost toResponse(Post post) {
        return new ResponsePost(
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getImageResource(),
                post.getContent(),
                post.getUpdatedAt(),
                post.getCreatedAt()
        );
    }

    public Post toEntity(CreatePost createPost) {
        Post post = new Post();
        post.setTitle(createPost.getTitle());
        post.setAuthor(createPost.getAuthor());
        post.setImageResource(createPost.getImageResource());
        post.setContent(createPost.getContent());

        return post;
    }
}