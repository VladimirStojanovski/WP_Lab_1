package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.wp.lab.repository.jpa.EventRepository;
import mk.finki.ukim.wp.lab.service.EventService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Event> listAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvents(String text, Double minRating){
        return eventRepository.findAll().stream()
                .filter(event -> (event.getName().contains(text) || event.getDescription().contains(text)) &&
                        event.getPopularityScore() >= minRating)
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> searchEventsByCategory(String categoryName) {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .filter(event -> event.getCategory() != null && event.getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }

    @Override
    public Event getEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event with ID " + id + " not found!"));
    }

    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteEventById(long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Event with ID " + id + " does not exist!");

        }
    }

    @Override
    public void like(long id) {
        for (Event event : eventRepository.findAll()) {
            if (event.getId() == id && !event.isLiked()) {
                event.setPopularityScore(event.getPopularityScore() + 1);
                event.setLiked(true);
            }
        }
    }

}