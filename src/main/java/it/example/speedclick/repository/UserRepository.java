package it.example.speedclick.repository;

import it.example.speedclick.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByNameAndSurname(String name, String surname);
}
