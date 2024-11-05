package com.jsf.Roadies.controller;

import com.jsf.Roadies.Exceptions.UserAlreadyExistsException;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.request.CreateUserRequest;
import com.jsf.Roadies.request.UpdateUserRequest;
import com.jsf.Roadies.response.ApiResponse;
import com.jsf.Roadies.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
    private final UserService userService;

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("success",userDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest req) {
        try {
            User user = userService.createUser(req);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("success",userDTO));
        }catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest req) {
        try {
            User user = userService.updateUser(req,userId);
            UserDTO userDTO = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("success",userDTO));
        }catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("success",userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error",e.getMessage()));
        }
    }
}
