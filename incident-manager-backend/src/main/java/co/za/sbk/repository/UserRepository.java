package co.za.sbk.repository;

import co.za.sbk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdnumber(String idNumber);
}
