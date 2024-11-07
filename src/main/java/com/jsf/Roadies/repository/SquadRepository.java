package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {

    @Query("SELECT s FROM Squad s JOIN s.userSquads m WHERE m.id = :userId")
    List<Squad> findSquadsByUserId(Long userId);

    @Query("SELECT s FROM Squad s LEFT JOIN FETCH s.userSquads WHERE s.id = :squadId")
    Optional<Squad> findSquadDetailsById(Long squadId);

    Boolean existsBySquadName(String squadName);
}
