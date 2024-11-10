package mk.finki.ukim.wp.lab.repository;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LocationRepository {

    private List<Location> locations;

    public LocationRepository() {
        this.locations = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            locations.add(new Location((long)i, "LocationName" + i, "LocationAddress" + i, "LocationCapacity" + i, "LocationDesc" +i));
        }
    }

    public List<Location> findAll() { return locations; }
}
