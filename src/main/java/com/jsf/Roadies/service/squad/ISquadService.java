package com.jsf.Roadies.service.squad;

import com.jsf.Roadies.dto.SquadDTO;
import com.jsf.Roadies.model.Squad;
import com.jsf.Roadies.request.CreateSquadRequest;

import java.util.List;

public interface ISquadService {
    Squad getSquadById(Long id);
    List<Squad> getAllSquads();

    Squad createSquad(CreateSquadRequest request);
    Squad updateSquad(CreateSquadRequest request,Long id);
    void deleteSquad(Long id);

    SquadDTO convertToDTO(Squad squad);
}
