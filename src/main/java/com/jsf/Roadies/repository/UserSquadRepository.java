package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.model.UserSquad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSquadRepository extends JpaRepository<UserSquad, Long> {
    @Query("SELECT u FROM User u JOIN UserSquad us ON u.id = us.user.id WHERE us.squad.id = :squadId")
    List<User> findUsersBySquadId(@Param("squadId") Long squadId);

    @Query("SELECT s FROM Squad s JOIN UserSquad us ON s.id= us.squad.id WHERE us.user.id = :userId")
    List<Squad> findSquadsByUserId(@Param("userId") Long userId);

    Optional<UserSquad> findByUser_IdAndSquad_Id(Long userId, Long squadId);
    Boolean existsByUser_IdAndSquad_Id(Long userId, Long squadId);
}
