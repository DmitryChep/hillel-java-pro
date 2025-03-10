package ua.ithillel.javapro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.service.interfaces.PostService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable @Valid Long id) {
        List<PostDTO> postsByUserId = postService.getPostsByUserId(id);
        return new ResponseEntity<>(postsByUserId, HttpStatus.OK);
    }
}
