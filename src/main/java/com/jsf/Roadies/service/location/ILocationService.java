package com.jsf.Roadies.service.location;

import com.jsf.Roadies.model.Location;
import com.jsf.Roadies.request.CreateLocationRequest;

public interface ILocationService {
    Location createLocation(CreateLocationRequest request);
    Location getUserLocation(Long id);
}
