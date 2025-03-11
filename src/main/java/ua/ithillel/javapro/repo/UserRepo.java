package ua.ithillel.javapro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.javapro.domain.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

