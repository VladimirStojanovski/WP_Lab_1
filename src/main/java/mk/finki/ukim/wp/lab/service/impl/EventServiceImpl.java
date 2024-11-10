package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.repository.EventRepository;
import mk.finki.ukim.wp.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event findById(long id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
            else return null;
        }
        return null;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text, Double minRating) {
        return eventRepository.searchEvents(text, minRating);
    }
}