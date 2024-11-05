package com.jsf.Roadies.service;

import com.jsf.Roadies.Exceptions.UserAlreadyExistsException;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.request.CreateUserRequest;
import com.jsf.Roadies.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Invalid user id"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(u -> !userRepository.existsByName(u.getUsername())
                        &&
                        !userRepository.existsByPhoneNumber(u.getPhoneNumber())
                )
                .map(req -> {
                    User user = new User();
                    user.setName(req.getUsername());
                    user.setPhoneNumber(req.getPhoneNumber());
                    user.setBio(req.getBio());
                    user.setPasswordHash(req.getPassword());
                    userRepository.save(user);
                    return user;
                }).orElseThrow(() -> new UserAlreadyExistsException("User already exists"))
                ;
    }

    @Override
    public User updateUser(UpdateUserRequest req, long id) {
        return userRepository
                .findById(id).map(existingUser -> {
                    existingUser.setName(req.getUsername());
                    existingUser.setBio(req.getBio());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new UserNotFoundException("Invalid user id"));
    }

    @Override
    public void deleteUser(long id) {
        userRepository
                .findById(id).ifPresentOrElse(userRepository::delete, () -> {
                    throw new UserNotFoundException("Invalid user id");
                });


    }
    @Override
    public UserDTO convertUserToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
