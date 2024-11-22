package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.service.EventService;
import mk.finki.ukim.wp.lab.service.LocationService;
import mk.finki.ukim.wp.lab.repository.EventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;
    private final EventRepository eventRepository;

    public EventController(EventService eventService, LocationService locationService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Event> events = eventService.listAll();
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

        Event event = new Event((long) (Math.random() * 1000), name, description, popularityScore, location);
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

        Event event = eventRepository.findById(eventId);
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);

        eventRepository.save(event);

        return "redirect:/events";
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {

        Event eventToDelete = eventRepository.findById(eventId);

        if (eventToDelete != null) {
            eventRepository.findAll().remove(eventToDelete);
        }

        return "redirect:/events";
    }

    @GetMapping("/like/{eventId}")
    public String likeEvent(@PathVariable Long eventId) {

        Event eventToLike = eventRepository.findById(eventId);

        if (eventToLike != null) {
            eventRepository.like(eventToLike.getId());
        }

        return "redirect:/events";
    }
}