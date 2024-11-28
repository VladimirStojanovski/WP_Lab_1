package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.repository.InMemoryLocationRepository;
import mk.finki.ukim.wp.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final InMemoryLocationRepository inMemoryLocationRepository;

    public LocationServiceImpl(InMemoryLocationRepository inMemoryLocationRepository) {
        this.inMemoryLocationRepository = inMemoryLocationRepository;
    }

    @Override
    public List<Location> findAll() {
        return inMemoryLocationRepository.findAll();
    }
}