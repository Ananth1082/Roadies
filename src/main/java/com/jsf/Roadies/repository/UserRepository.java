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
    User findByPhoneNumber(String email);

    User findByName(String username);

    @Query("SELECT new com.jsf.Roadies.dto.UserDTO(u.name, u.image.id) FROM User u")
    List<UserDTO> findAllUsersWithNameAndImage();

    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByName(String name);

    Object save(CreateUserRequest createUserRequest);
}
