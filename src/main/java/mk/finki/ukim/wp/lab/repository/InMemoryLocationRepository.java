package mk.finki.ukim.wp.lab.repository;

import mk.finki.ukim.wp.lab.bootstrap.DataHolder;
import mk.finki.ukim.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryLocationRepository {

    public Location save(Location location) {
        DataHolder.locations.removeIf(c -> c.getName().equals(location.getName()));

        DataHolder.locations.add(location);

        return location;
    }

    public List<Location> findAll() {
        return DataHolder.locations;
    }
}
