package com.jsf.Roadies.service.usersquad;

import com.jsf.Roadies.Exceptions.*;
import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.model.UserSquad;
import com.jsf.Roadies.repository.SquadRepository;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.repository.UserSquadRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSquadService implements IUserSquadService {
    private final UserSquadRepository userSquadRepository;
    private final UserRepository userRepository;
    private final SquadRepository squadRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<User> getSquadMembers(Long squadId) {
        return userSquadRepository.findUsersBySquadId(squadId);
    }

    @Override
    public List<Squad> getUserSquads(Long userId) {
        return userSquadRepository.findSquadsByUserId(userId);
    }

    @Override
    public void joinSquad(Long userId, Long squadId) {
        if (userSquadRepository.existsByUser_IdAndSquad_Id(userId, squadId)){
            throw new MemberAlreadyExists("User already exists");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Invalid User ID"));
        Squad squad = squadRepository.findById(squadId).orElseThrow(() -> new SquadNotFoundException("Invalid Squad ID"));
        UserSquad us = new UserSquad();
        us.setUser(user);
        us.setSquad(squad);
        userSquadRepository.save(us);
    }

    @Override
    public void leaveSquad(Long userId, Long squadId) {
        userSquadRepository.findByUser_IdAndSquad_Id(userId, squadId)
                .ifPresentOrElse(userSquadRepository::delete, () -> {
                    throw new NotAMemberException("User is not a member of squad");
                });
    }
}
