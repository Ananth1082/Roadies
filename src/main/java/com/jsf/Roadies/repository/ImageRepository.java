package com.jsf.Roadies.repository;

import com.jsf.Roadies.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteByUserId(long userId);
    Optional<Image> findByUserId(long userId);

    Boolean existsByUserId(long userId);
}
