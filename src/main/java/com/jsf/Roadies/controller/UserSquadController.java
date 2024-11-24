package com.jsf.Roadies.controller;

import com.jsf.Roadies.dto.SquadDTO;
import com.jsf.Roadies.dto.UserDTO;
import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.request.JoinSquadRequest;
import com.jsf.Roadies.response.ApiResponse;
import com.jsf.Roadies.service.squad.SquadService;
import com.jsf.Roadies.service.user.UserService;
import com.jsf.Roadies.service.usersquad.UserSquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/members")
public class UserSquadController {
    private final UserSquadService userSquadService;
    private final UserService userService;
    private final SquadService squadService;

    @GetMapping("squad/{squadId}")
    public ResponseEntity<ApiResponse> getSquadMembers(@PathVariable Long squadId) {
        try {
            List<UserDTO> memberList = userSquadService.
                    getSquadMembers(squadId)
                    .stream()
                    .map(userService::convertUserToDto)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("success",memberList));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ApiResponse> getUserSquads(@PathVariable Long userId) {
        try {
            List<SquadDTO> squads = userSquadService.getUserSquads(userId)
                    .stream()
                    .map(squadService::convertToDTO)
                    .toList();
            return ResponseEntity.ok(new ApiResponse("success",squads));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PostMapping("join")
    public ResponseEntity<ApiResponse> joinSquad(@RequestBody JoinSquadRequest req) {
        try {
            userSquadService.joinSquad(req.getUserId(), req.getSquadId());
            return ResponseEntity.ok(new ApiResponse("user joined group",null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @DeleteMapping("leave")
    public ResponseEntity<ApiResponse> leaveSquad(@RequestBody JoinSquadRequest req) {
        try {
            userSquadService.leaveSquad(req.getUserId(), req.getSquadId());
            return ResponseEntity.ok(new ApiResponse("user left group",null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }
}
