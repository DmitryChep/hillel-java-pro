package ua.ithillel.javapro.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.model.Post;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {

    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
}
