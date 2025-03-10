package ua.ithillel.javapro.service.implementations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.mapper.PostMapper;
import ua.ithillel.javapro.domain.model.Post;
import ua.ithillel.javapro.repo.PostRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepo postRepo;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceIml postService;

    @Test
    public void getPostsByUserId_shouldReturnPosts_whenUserExists() {
        Long userId = 1L;
        Post post = new Post(1L, "Post Title", "Post Content", null);
        PostDTO postDTO = new PostDTO(1L, "Post Title", "Post Content");

        when(postRepo.getPostsByUserId(userId)).thenReturn(List.of(post));
        when(postMapper.postToPostDTO(post)).thenReturn(postDTO);

        List<PostDTO> result = postService.getPostsByUserId(userId);

        Assertions.assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Post Title", result.get(0).title());
        verify(postRepo).getPostsByUserId(userId);
    }

    @Test
    public void getPostsByUserId_shouldThrowException_whenUserIdIsInvalid() {
        Long userId = -1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.getPostsByUserId(userId);
        });

        assertEquals("userId is invalid, method: getPostsByUserId, userId: -1", exception.getMessage());
    }
}
