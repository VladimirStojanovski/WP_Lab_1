package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.service.EventService;
import mk.finki.ukim.wp.lab.service.LocationService;
import mk.finki.ukim.wp.lab.repository.InMemoryEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final InMemoryEventRepository inMemoryEventRepository;

    public EventController(EventService eventService, LocationService locationService, InMemoryEventRepository inMemoryEventRepository) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.inMemoryEventRepository = inMemoryEventRepository;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String searchText,
                                @RequestParam(required = false) String minRatingStr,
                                @RequestParam(required = false) String error,
                                Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Event> events;

        if ((searchText != null && !searchText.isEmpty()) || (minRatingStr != null && !minRatingStr.isEmpty())){
            double minRating = Double.parseDouble(minRatingStr);
            events = eventService.searchEvents(searchText, minRating);
        } else {
            events = eventService.listAll();
        }

        model.addAttribute("events", events);

        return "listEvents";
    }

    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {

        Location location = locationService.findAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Event event = new Event( name, description, popularityScore, location);
        eventService.addEvent(event);

        return "redirect:/events";
    }

    @GetMapping("/add")
    public String addEventPage(Model model) {
        List<Event> events = this.eventService.listAll();
        List<Location> locations = this.locationService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("locations", locations);
        return "addEvent";
    }


    @GetMapping("/edit/{eventId}")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {

        if (eventService.getEventById(eventId) == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Event not found");
            return "redirect:/events";
        }

        model.addAttribute("event", eventService.getEventById(eventId));
        model.addAttribute("locations", locationService.findAll());
        return "addEvent";
    }


    @PostMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {

        Location location = locationService.findAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Event event = inMemoryEventRepository.findById(eventId);
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);

        inMemoryEventRepository.save(event);

        return "redirect:/events";
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {

        Event eventToDelete = inMemoryEventRepository.findById(eventId);

        if (eventToDelete != null) {
            inMemoryEventRepository.findAll().remove(eventToDelete);
        }

        return "redirect:/events";
    }

    @GetMapping("/like/{eventId}")
    public String likeEvent(@PathVariable Long eventId) {

        Event eventToLike = inMemoryEventRepository.findById(eventId);

        if (eventToLike != null) {
            inMemoryEventRepository.like(eventToLike.getId());
        }

        return "redirect:/events";
    }
}