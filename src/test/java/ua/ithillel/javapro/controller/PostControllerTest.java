package ua.ithillel.javapro.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.service.interfaces.PostService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    public void getPostsByUserId_shouldReturnPosts_whenUserExists() {
        Long userId = 1L;
        PostDTO postDTO = new PostDTO(1L, "Post Title", "Post Content");

        when(postService.getPostsByUserId(userId)).thenReturn(List.of(postDTO));

        ResponseEntity<List<PostDTO>> response = postController.getPostsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Post Title", response.getBody().get(0).title());
        verify(postService).getPostsByUserId(userId);
    }
}
