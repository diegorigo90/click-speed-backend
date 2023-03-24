package it.example.speedclick.repository;

import it.example.speedclick.dto.Time;
import it.example.speedclick.dto.UserBestTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TimesRepository extends JpaRepository<Time, String> {

    Time findFirstByOrderByTime();

    @Query(
            value = "SELECT name, surname, user_id, min(time) as time " +
                    "FROM times t " +
                    "JOIN users u on u.id = t.user_id group by name, surname, user_id order by time asc",
            nativeQuery = true)
    List<Object[]> getClassification();
}
