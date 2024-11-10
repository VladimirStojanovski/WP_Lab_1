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

    // Method to display the form for adding a new event
    @GetMapping("/add")
    public String getAddEventPage(Model model) {
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        return "addEvent";
    }

    // Method to handle the form submission for adding a new event
    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {

        // Find the location by ID
        Location location = locationService.findAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        // Create and save the event
        Event event = new Event((long) (Math.random() * 1000), name, description, popularityScore, location);
        eventRepository.findAll().add(event);  // Add event to the repository

        // Redirect to the list of events
        return "redirect:/events";
    }

    // Method to display the form for editing an existing event
    @GetMapping("/edit/{eventId}")
    public String getEditEventPage(@PathVariable Long eventId, Model model) {
        Event event = eventRepository.findById(eventId);
        List<Location> locations = locationService.findAll();

        model.addAttribute("event", event);
        model.addAttribute("locations", locations);
        return "editEvent";
    }

    // Method to handle the form submission for updating an event
    @PostMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {

        // Find the location by ID
        Location location = locationService.findAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        // Find the event by ID and update it
        Event event = eventRepository.findById(eventId);
        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);

        // Save the updated event
       // eventRepository.save(event);

        // Redirect to the list of events
        return "redirect:/events";
    }
}
