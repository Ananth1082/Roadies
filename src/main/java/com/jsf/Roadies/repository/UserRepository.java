package com.jsf.Roadies.repository;

import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.request.CreateUserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String email);

    User findByName(String username);

    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByName(String name);

}
