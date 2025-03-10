package ua.ithillel.javapro.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ithillel.javapro.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    @Query("SELECT u FROM t_user u WHERE SUBSTRING(u.email, LOCATE('@', u.email) + 1) = :domain")
    List<User> findByDomain(@Param("domain") String domain);
}
