package com.lucasnvs.trilho.content.services;

import com.lucasnvs.trilho.content.domain.Post;
import com.lucasnvs.trilho.content.dto.CreatePost;
import com.lucasnvs.trilho.content.dto.ResponsePost;
import com.lucasnvs.trilho.content.repositories.PostRepository;
import com.lucasnvs.trilho.shared.services.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private FileStorageService fileStorageService;

    @Autowired
    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all posts successfully")
    void testGetAllPosts() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setAuthor("Test Author");
        post.setImageResource("test.jpg");
        post.setContent("Test Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));
        when(postMapper.toResponse(post)).thenReturn(new ResponsePost(1L, "Test Title", "Test Author", "test.jpg", "Test Content", LocalDateTime.now(), LocalDateTime.now()));

        List<ResponsePost> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(1, posts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return post successfully")
    void testGetPostById() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setAuthor("Test Author");
        post.setImageResource("test.jpg");
        post.setContent("Test Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postMapper.toResponse(post)).thenReturn(new ResponsePost(1L, "Test Title", "Test Author", "test.jpg", "Test Content", LocalDateTime.now(), LocalDateTime.now()));

        ResponsePost responsePost = postService.getPostById(1L);

        assertNotNull(responsePost);
        assertEquals("Test Title", responsePost.getTitle());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should save post successfully")
    void testSavePost() {
        CreatePost createPost = new CreatePost();
        createPost.setTitle("Test Title");
        createPost.setAuthor("Test Author");

        byte[] imageBytes = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                imageBytes
        );

        createPost.setImageResource(mockFile);
        createPost.setContent("Test Content");

        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setAuthor("Test Author");
        post.setImageResource("test.jpg");
        post.setContent("Test Content");
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        when(postMapper.toEntity(createPost)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toResponse(post)).thenReturn(new ResponsePost(1L, "Test Title", "Test Author", "test.jpg", "Test Content", LocalDateTime.now(), LocalDateTime.now()));

        ResponsePost responsePost = postService.savePost(createPost);

        assertNotNull(responsePost);
        assertEquals("Test Title", responsePost.getTitle());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("Should delete post successfully when it exists")
    void deletePostCase1() {
        Long postId = 1L;
        when(postRepository.existsById(postId)).thenReturn(true);

        boolean result = postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
        assertFalse(result);
    }

    @Test
    @DisplayName("Should throw exception when post does not exist")
    void deletePostCase2() {
        Long postId = 1L;
        when(postRepository.existsById(postId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> postService.deletePost(postId));
        assertEquals("Post with ID " + postId + " not found", exception.getMessage());

        verify(postRepository, never()).deleteById(anyLong());
    }
}