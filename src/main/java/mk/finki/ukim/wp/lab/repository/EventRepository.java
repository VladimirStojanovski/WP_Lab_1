package mk.finki.ukim.wp.lab.repository;

import mk.finki.ukim.wp.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EventRepository {
    private List<Event> events;

    public EventRepository() {
        this.events = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            events.add(new Event("Event" + i, "Description for Event" + i, i * 10.0));
        }
    }

    public List<Event> findAll() {
        return events;
    }

    public List<Event> searchEvents(String text, Double minRating) {
        return events.stream()
                .filter(event -> (event.getName().contains(text) || event.getDescription().contains(text)) &&
                        event.getPopularityScore() >= minRating)
                .collect(Collectors.toList());
    }
}