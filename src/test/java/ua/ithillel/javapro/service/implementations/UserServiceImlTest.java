package ua.ithillel.javapro.service.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.mapper.PostMapper;
import ua.ithillel.javapro.domain.mapper.UserMapper;
import ua.ithillel.javapro.domain.model.Post;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.repo.PostRepo;
import ua.ithillel.javapro.repo.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImlTest {

    @InjectMocks
    private UserServiceIml userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PostRepo postRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PostMapper postMapper;

    @Test
    public void getAllUsersByEmailDomain_shouldReturnUsers_whenUsersExist() {
        String domain = "example.com";
        User user = new User(1L, "John", "john@example.com", new ArrayList<>());
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com");

        when(userRepo.findByDomain(domain)).thenReturn(Collections.singletonList(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsersByEmailDomain(domain);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDTO, result.get(0));
    }

    @Test
    public void getUserByName_shouldReturnUser_whenUserExists() {
        String name = "John";
        User user = new User(1L, name, "john@example.com", new ArrayList<>());
        UserDTO userDTO = new UserDTO(1L, name, "john@example.com");

        when(userRepo.findByName(name)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserByName(name);

        assertNotNull(result);
        assertEquals(userDTO, result);
    }

    @Test
    public void createUser_shouldReturnUser_whenUserIsCreated() {
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com");
        PostDTO postDTO = new PostDTO(1L, "test", "test");
        User user = new User(1L, "John", "john@example.com", new ArrayList<>());
        Post post = new Post();
        UserDTO createdUserDTO = new UserDTO(1L, "John", "john@example.com");

        when(userRepo.findByName(userDTO.name())).thenReturn(Optional.empty());
        when(userMapper.userDTOtoUser(userDTO)).thenReturn(user);
        when(postMapper.postDTOToPost(postDTO)).thenReturn(post);
        when(userRepo.save(user)).thenReturn(user);
        when(postRepo.save(post)).thenReturn(post);
        when(userMapper.userToUserDTO(user)).thenReturn(createdUserDTO);

        UserDTO result = userService.createUser(userDTO, postDTO);

        assertNotNull(result);
        assertEquals(createdUserDTO, result);
    }

    @Test
    public void updateUser_shouldReturnUpdatedUser_whenUserExists() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com");
        User user = new User(1L, "John", "john@example.com", new ArrayList<>());
        UserDTO updatedUserDTO = new UserDTO(1L, "John", "john@example.com");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateUserFromDTO(userDTO, user);
        when(userRepo.save(user)).thenReturn(user);
        when(userMapper.userToUserDTO(user)).thenReturn(updatedUserDTO);

        UserDTO result = userService.updateUser(userId, userDTO);

        assertNotNull(result);
        assertEquals(updatedUserDTO, result);
    }

    @Test
    public void deleteUser_shouldReturnUser_whenUserIsDeleted() {
        Long userId = 1L;
        User user = new User(1L, "John", "john@example.com", new ArrayList<>());
        UserDTO deletedUserDTO = new UserDTO(1L, "John", "john@example.com");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepo).delete(user);
        when(userMapper.userToUserDTO(user)).thenReturn(deletedUserDTO);

        UserDTO result = userService.deleteUser(userId);

        assertNotNull(result);
        assertEquals(deletedUserDTO, result);
    }
}
