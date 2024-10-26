package mk.finki.ukim.wp.lab.repository;

import mk.finki.ukim.wp.lab.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {
    private List<Event> events = new ArrayList<>();

    public EventRepository() {
        events.add(new Event("sampleEvent1", "sampleDesc1", 1.0));
        events.add(new Event("sampleEvent2", "sampleDesc2", 2.0));
        events.add(new Event("sampleEvent3", "sampleDesc3", 3.0));
        events.add(new Event("sampleEvent4", "sampleDesc4", 4.0));
        events.add(new Event("sampleEvent5", "sampleDesc5", 5.0));
        events.add(new Event("sampleEvent6", "sampleDesc6", 6.0));
        events.add(new Event("sampleEvent7", "sampleDesc7", 7.0));
        events.add(new Event("sampleEvent8", "sampleDesc8", 8.0));
        events.add(new Event("sampleEvent9", "sampleDesc9", 9.0));
        events.add(new Event("sampleEvent10", "sampleDesc10", 10.0));
    }

    public List<Event> findAll() {
        return events;
    }

    public List<Event> searchEvents(String text) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.getName().toLowerCase().contains(text.toLowerCase()) ||
                    event.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }

//    public List<Event> searchEvents(String text) {
//        return events.stream()
//                .filter(event -> event.getName().contains(text) || event.getDescription().contains(text))
//                .toList();
//    }
//    possible errors with lower/capital case conversion?

}
