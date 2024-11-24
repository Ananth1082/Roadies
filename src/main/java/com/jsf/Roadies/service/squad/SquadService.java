package com.jsf.Roadies.service.squad;

import com.jsf.Roadies.Exceptions.SquadAlreadyExistsException;
import com.jsf.Roadies.Exceptions.SquadNotFoundException;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.dto.SquadDTO;
import com.jsf.Roadies.enums.GroupRoleType;
import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.model.UserSquad;
import com.jsf.Roadies.repository.SquadRepository;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.repository.UserSquadRepository;
import com.jsf.Roadies.request.CreateSquadRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SquadService implements ISquadService {
    private final SquadRepository squadRepository;
    private final ModelMapper modelMapper;
    private final UserSquadRepository userSquadRepository;
    private final UserRepository userRepository;


    @Override
    public Squad getSquadById(Long id) {
        return squadRepository.findSquadDetailsById(id)
                .orElseThrow(()-> new SquadNotFoundException("Invalid squad id"));
    }

    @Override
    public List<Squad> getAllSquads() {
        return squadRepository.findAll();
    }

    @Override
    public Squad createSquad(CreateSquadRequest request) {
        return Optional.of(request)
                .filter(s -> !squadRepository.existsBySquadName(s.getSquadName()))
                .map(req -> {

                    Squad squad = new Squad();
                    squad.setSquadName(req.getSquadName());
                    squad.setSquadCapacity(req.getSquadCapacity());
                    squad.setSquadRange(req.getSquadRange());
                    squad.setSquadDescription(req.getSquadDescription());
                    squadRepository.save(squad);

                    UserSquad us = new UserSquad();
                    us.setUser(userRepository.findById(req.getUserId()).orElseThrow(()->new UserNotFoundException("Invalid user id")));
                    us.setSquad(squad);
                    us.setRole(GroupRoleType.ADMIN);
                    userSquadRepository.save(us);
                    return squad;
                }).orElseThrow(() -> new SquadAlreadyExistsException("Squad already exists"));
    }

    @Override
    public Squad updateSquad(CreateSquadRequest req,Long id) {
        return squadRepository
                .findById(id).map(existingSquad -> {
                    existingSquad.setSquadName(req.getSquadName());
                    existingSquad.setSquadCapacity(req.getSquadCapacity());
                    existingSquad.setSquadRange(req.getSquadRange());
                    existingSquad.setSquadDescription(req.getSquadDescription());
                    existingSquad.setSquadDescription(req.getSquadDescription());
                    return squadRepository.save(existingSquad);
                }).orElseThrow(() -> new UserNotFoundException("Invalid user id"));
    }

    @Override
    public void deleteSquad(Long id) {
        squadRepository.findById(id)
                .ifPresentOrElse(squadRepository::delete, ()-> {
                    throw new SquadNotFoundException("Invalid squad id");
                });
    }

    @Override
    public SquadDTO convertToDTO(Squad squad) {
        return modelMapper.map(squad, SquadDTO.class);
    }
}
