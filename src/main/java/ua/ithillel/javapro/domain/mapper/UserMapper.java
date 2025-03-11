package ua.ithillel.javapro.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.model.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOtoUser(UserDTO userDTO);
}
