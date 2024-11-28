package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.repository.jpa.EventRepository;
import mk.finki.ukim.wp.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository EventRepository;

    public EventServiceImpl(EventRepository EventRepository) {
        this.EventRepository = EventRepository;
    }

    @Override
    public List<Event> listAll() {
        return EventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text, Double minRating) {
        return EventRepository.searchEvents(text, minRating);
    }

    @Override
    public Optional<Event> getEventById(long id){
        return EventRepository.findById(id);
    }

    @Override
    public Event addEvent(Event event) {
        return EventRepository.save(event);
    }
}