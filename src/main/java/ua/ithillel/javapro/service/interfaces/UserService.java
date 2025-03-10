package ua.ithillel.javapro.service.interfaces;

import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsersByEmailDomain(String domain);
    UserDTO getUserByName(String name);
    UserDTO createUser(UserDTO userDTO, PostDTO postDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO deleteUser(Long userId);
}
