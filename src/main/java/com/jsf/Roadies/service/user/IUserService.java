package com.jsf.Roadies.service.user;

import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.request.CreateUserRequest;
import com.jsf.Roadies.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    User getUserById(long id);
    List<User> getAllUsers();
    User getUserByUserName(String username);
    User getUserByPhoneNumber(String phoneNumber);
    User createUser(CreateUserRequest req);
    User updateUser(UpdateUserRequest req, long id);
    void deleteUser(long id);
    UserDTO convertUserToDto(User user);
}
