package com.jsf.Roadies.service.user;

import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.request.CreateUserRequest;
import com.jsf.Roadies.request.LoginRequest;
import com.jsf.Roadies.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    User getUserById(long id);
    Long login(LoginRequest req);
    List<User> getAllUsers();
    User getUserByUserName(String username);
    User createUser(CreateUserRequest req);
    User updateUser(UpdateUserRequest req, long id);
    void deleteUser(long id);

    UserDTO convertUserToDto(User user);
}
