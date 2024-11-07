package com.jsf.Roadies.service.usersquad;

import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.model.User;

import java.util.List;

public interface IUserSquadService {
    List<User> getSquadMembers(Long squadId);
    List<Squad> getUserSquads(Long userId);
    void joinSquad(Long userId,Long squadId);
    void leaveSquad(Long userId,Long squadId);
}
