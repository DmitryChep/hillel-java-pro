package ua.ithillel.javapro.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.mapper.PostMapper;
import ua.ithillel.javapro.domain.model.Post;
import ua.ithillel.javapro.repo.PostRepo;
import ua.ithillel.javapro.service.interfaces.PostService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostServiceIml implements PostService {

    @Autowired
    private final PostRepo postRepo;
    private final PostMapper postMapper;


    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        if (userId == null|| userId <= 0) {
            log.error("userId is invalid, method: getPostsByUserId, userId: {}", userId);
            throw new IllegalArgumentException("userId is invalid, method: getPostsByUserId, userId: " + userId);
        }
        log.info("Get all posts by user id");
        log.debug("userId: {}", userId);

        List<Post> postByUserId = postRepo.getPostsByUserId(userId);

        return postByUserId.stream()
                .map(postMapper::postToPostDTO)
                .toList();
    }
}


