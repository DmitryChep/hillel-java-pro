package ua.ithillel.javapro.service.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.mapper.PostMapper;
import ua.ithillel.javapro.domain.mapper.UserMapper;
import ua.ithillel.javapro.domain.model.Post;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.exception.NotFoundServiceException;
import ua.ithillel.javapro.repo.PostRepo;
import ua.ithillel.javapro.repo.UserRepo;
import ua.ithillel.javapro.service.interfaces.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceIml implements UserService {
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final UserMapper userMapper;
    private final PostMapper postMapper;


    @Override
    public List<UserDTO> getAllUsersByEmailDomain(String userDomain) {
        if (userDomain == null || userDomain.isEmpty()) {
            log.error("Domain is invalid, method: getPostsByUserId, userDomain: {}", userDomain);
            throw new IllegalArgumentException("userDomain is invalid, method: getPostsByUserId, userDomain:" + userDomain);
        }
        log.info("Get all users by email domain");
        log.debug("userDomain: {}", userDomain);

        List<User> usersByEmailDomain = userRepo.findByDomain(userDomain);
        if (usersByEmailDomain.isEmpty()) {
            log.warn("User by domain not found, method: getAllUsersByEmailDomain, userDomain: {}", userDomain);
        }

        return usersByEmailDomain.stream()
                .map(userMapper::userToUserDTO)
                .toList();
    }


    @Override
    public UserDTO getUserByName(String name) {
        log.info("Get user by name");
        log.debug("userName:  {}", name);

        Optional<User> userByUserName = userRepo.findByName(name);
        User user = userByUserName.orElseThrow(() -> new NotFoundServiceException("User with name: " + name + " not found"));

        return userMapper.userToUserDTO(user);
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO, PostDTO postDTO) {
        // Check for user duplication
        Optional<User> existingUser = userRepo.findByName(userDTO.name());
        if (existingUser.isPresent()) {
            log.error("User with username {} already exists", userDTO.name());
            throw new IllegalArgumentException("User with the name " + userDTO.name() + " already exists");
        }

        log.info("Create user with post {}", postDTO);
        log.debug("user: {}", userDTO);

        User user = userMapper.userDTOtoUser(userDTO);
        Post post = postMapper.postDTOToPost(postDTO);

        post.setUser(user);
        user.getPosts().add(post);

        User savedUser = userRepo.save(user);
        postRepo.save(post);

        return userMapper.userToUserDTO(savedUser);
    }


    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (id == null || id == 0) {
            log.error("User id is invalid, method: updateUser, id: {}", id);
            throw new IllegalArgumentException("User id is invalid, method: updateUser, id:" + id);
        }
        log.info("Update user");
        log.debug("userDTO: {}", userDTO);

        Optional<User> userById = userRepo.findById(id);
        User user = userById.orElseThrow(() -> new NotFoundServiceException("User with ID " + id + " not found"));

        userMapper.updateUserFromDTO(userDTO, user);

        userRepo.save(user);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO deleteUser(Long userId) {
        if (userId == null || userId == 0) {
            log.error("User id is invalid, method: updateUser, id: {}", userId);
            throw new IllegalArgumentException("User id is invalid, method: updateUser, id:" + userId);
        }
        log.info("Delete user");
        log.debug("userId: {}", userId);

        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundServiceException("User with ID " + userId + " not found"));

        userRepo.delete(user);

        return userMapper.userToUserDTO(user);
    }
}
