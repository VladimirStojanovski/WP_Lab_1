package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.repository.jpa.LocationRepository;
import mk.finki.ukim.wp.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }
}