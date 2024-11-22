package mk.finki.ukim.wp.lab.service;

import mk.finki.ukim.wp.lab.model.Event;
import java.util.List;

public interface EventService {
    List<Event> listAll();
    List<Event> searchEvents(String text, Double minRating);
    Event getEventById(long id);
    Event addEvent(Event event);
}
