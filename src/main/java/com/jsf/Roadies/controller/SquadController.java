package com.jsf.Roadies.controller;

import com.jsf.Roadies.Exceptions.SquadAlreadyExistsException;
import com.jsf.Roadies.Exceptions.SquadNotFoundException;
import com.jsf.Roadies.dto.SquadDTO;
import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.request.CreateSquadRequest;
import com.jsf.Roadies.request.CreateUserRequest;
import com.jsf.Roadies.response.ApiResponse;
import com.jsf.Roadies.service.squad.SquadService;
import com.jsf.Roadies.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/squad")
public class SquadController {
    private final SquadService squadService;
    private final UserService userService;

    @GetMapping("{squadId}")
    public ResponseEntity<ApiResponse> getSquadDetails(@PathVariable Long squadId) {
        try {
            Squad squad = squadService.getSquadById(squadId);
            SquadDTO squadDTO = squadService.convertToDTO(squad);
            return ResponseEntity.ok(new ApiResponse("squad", squadDTO));
        } catch ( SquadNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

//    @GetMapping("user/{userId}")
//    public ResponseEntity<ApiResponse> getUserSquad(@PathVariable Long userId) {
//        try {
//            Squad squad = squadService.getSquadBy(userId);
//
//        }
//    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createSquad(@RequestBody CreateSquadRequest request) {
        try {
            Squad squad = squadService.createSquad(request);
            SquadDTO squadDTO = squadService.convertToDTO(squad);
            return ResponseEntity.ok(new ApiResponse("squad", squadDTO));
        } catch (SquadAlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PutMapping("{userId}")
    public ResponseEntity<ApiResponse> updateSquad(@RequestBody CreateSquadRequest request, @PathVariable Long userId) {
        try {
            Squad squad = squadService.updateSquad(request, userId);
            SquadDTO squadDTO = squadService.convertToDTO(squad);
            return ResponseEntity.ok(new ApiResponse("squad", squadDTO));
        } catch (SquadNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse> deleteSquad(@PathVariable Long userId) {
        try {
            squadService.deleteSquad(userId);
            return ResponseEntity.ok(new ApiResponse("success",null));
        } catch (SquadNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }
}
