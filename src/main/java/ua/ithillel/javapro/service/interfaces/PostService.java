package ua.ithillel.javapro.service.interfaces;

import ua.ithillel.javapro.domain.dto.PostDTO;

import java.util.List;

public interface PostService {
   List<PostDTO> getPostsByUserId(Long userId);
}
