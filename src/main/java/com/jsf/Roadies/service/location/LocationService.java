package com.jsf.Roadies.service.location;

import com.jsf.Roadies.Exceptions.SquadAlreadyExistsException;
import com.jsf.Roadies.Exceptions.UserNotFoundException;
import com.jsf.Roadies.model.Location;
import com.jsf.Roadies.model.User;
import com.jsf.Roadies.repository.LocationRepository;
import com.jsf.Roadies.repository.UserRepository;
import com.jsf.Roadies.request.CreateLocationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public Location createLocation(CreateLocationRequest request) {
        return Optional.of(request)
                .map(req -> {
                    User user = userRepository.findById(req.getUserId()).orElseThrow(() -> new UserNotFoundException("User Not Found"));
                    Location location = new Location();
                    location.setUser(user);
                    location.setLatitude(req.getLatitude());
                    location.setLongitude(req.getLongitude());

                    location = locationRepository.save(location);
                    return location;
                }).orElseThrow(() -> new SquadAlreadyExistsException("Squad already exists"));
    }

    @Override
    public Location getUserLocation(Long userId) {
        return locationRepository.findLocationByUserId(userId);
    }
}
