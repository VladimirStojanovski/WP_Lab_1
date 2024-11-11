package mk.finki.ukim.wp.lab.repository;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    private List<Event> events;
    private LocationRepository locationRepository;

    public EventRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.events = new ArrayList<>();

        List<Location> locations = locationRepository.findAll();

        for (int i = 1; i <= 10; i++) {
            Location location = locations.get(i % locations.size());
            events.add(new Event((long) i,"Event" + i, "Description for Event" + i, i * 10.0, location));
        }
    }

    public List<Event> findAll() {
        return events;
    }

    public Event findById(long id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    public void like(long id) {
        for (Event event : events) {
            if (event.getId() == id && !event.isLiked()) {
                event.setPopularityScore(event.getPopularityScore() + 1);
                event.setLiked(true);
            }
        }
    }

    public List<Event> searchEvents(String text, Double minRating) {
        return events.stream()
                .filter(event -> (event.getName().contains(text) || event.getDescription().contains(text)) &&
                        event.getPopularityScore() >= minRating)
                .collect(Collectors.toList());
    }

    public Optional<Event> save(Event event) {
        Optional<Event> existingEvent = events.stream()
                .filter(e -> e.getId().equals(event.getId()))
                .findFirst();

        if (existingEvent.isPresent()) {
            events.remove(existingEvent.get());
        }
        events.add(event);
        return Optional.of(event);
    }

}