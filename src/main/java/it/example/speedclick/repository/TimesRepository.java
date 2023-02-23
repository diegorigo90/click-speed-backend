package it.example.speedclick.repository;

import it.example.speedclick.dto.Times;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesRepository extends JpaRepository<Times, String> {

    Times findFirstByOrderByTime();
}
