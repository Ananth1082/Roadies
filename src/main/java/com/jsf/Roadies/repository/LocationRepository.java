package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.user.id = :userId")
    Location findLocationByUserId(@Param("userId") Long userId);
}
