package ua.ithillel.javapro.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.mapper.UserMapper;
import ua.ithillel.javapro.domain.model.Role;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.repo.RoleRepo;
import ua.ithillel.javapro.repo.UserRepo;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDefaultServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private UserDefaultService userDefaultService;

    private User user;
    private Role userRole;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setName("USER");

        user = new User();
        user.setEmail("john@example.com");
        user.setPassword("hashedpassword");
        user.setRoles(Collections.singletonList(userRole));

        userDTO = new UserDTO("John", "Doe", "john@example.com", "password123");
    }

    @Test
    void loadUserByUsername_shouldReturnUser_whenUserExists() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        UserDetails userDetails = userDefaultService.loadUserByUsername(user.getEmail());

        userDetails.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }


    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserDoesNotExist() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userDefaultService.loadUserByUsername(user.getEmail()));
    }

    @Test
    void saveUser_shouldSaveUserWithRole() {
        when(roleRepo.findByName("ROLE_USER")).thenReturn(userRole);
        when(userMapper.userDTOtoUser(userDTO)).thenReturn(user);
        when(userRepo.save(user)).thenReturn(user);

        User savedUser = userDefaultService.saveUser(userDTO);

        assertNotNull(savedUser);
        assertEquals(userDTO.email(), savedUser.getEmail());
        assertEquals(1, savedUser.getRoles().size());
        assertEquals("USER", savedUser.getRoles().get(0).getName());

        verify(userRepo).save(user);
    }

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(user);

        User foundUser = userDefaultService.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers() {
        when(userRepo.findAll()).thenReturn(List.of(user));
        when(userMapper.userToUserDTO(user)).thenReturn(userDTO);

        List<UserDTO> users = userDefaultService.findAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
    }
}
