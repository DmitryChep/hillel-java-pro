package ua.ithillel.javapro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.mapper.UserMapper;
import ua.ithillel.javapro.domain.model.Role;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.repo.RoleRepo;
import ua.ithillel.javapro.repo.UserRepo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserDefaultService implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ua.ithillel.javapro.domain.model.User user = userRepo.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public User saveUser(UserDTO userDTO) {
        User user = userMapper.userDTOtoUser(userDTO);
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return userRepo.save(user);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }
}
