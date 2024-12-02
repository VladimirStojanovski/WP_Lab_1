package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.service.EventService;
import mk.finki.ukim.wp.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final LocationService locationService;

    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
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

        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("locations", locationService.findAll());
        return "addEvent";
    }


//    @PostMapping("/edit/{eventId}")
//    public String editEvent(@PathVariable Long eventId,
//                            @RequestParam String name,
//                            @RequestParam String description,
//                            @RequestParam double popularityScore,
//                            @RequestParam Long locationId) {
//
//        Location location = locationService.findAll().stream()
//                .filter(loc -> loc.getId().equals(locationId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));
//
//        Event event = inMemoryEventRepository.findById(eventId);
//        event.setName(name);
//        event.setDescription(description);
//        event.setPopularityScore(popularityScore);
//        event.setLocation(location);
//
//        inMemoryEventRepository.save(event);
//
//        return "redirect:/events";
//    }


    @PostMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId) {

        Event event = eventService.getEventById(eventId);

        Location location = locationService.findAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);

        eventService.addEvent(event);

        return "redirect:/events";
    }


//    @GetMapping("/delete/{eventId}")
//    public String deleteEvent(@PathVariable Long eventId) {
//
//        Event eventToDelete = eventService.getEventById(eventId);
//
//        if (eventToDelete != null) {
//            eventService.listAll().remove(eventToDelete);
//        }
//
//        return "redirect:/events";
//    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEventById(eventId);
        } catch (IllegalArgumentException e) {
            return "redirect:/events?error=" + e.getMessage();
        }
        return "redirect:/events";
    }


//    @GetMapping("/like/{eventId}")
//    public String likeEvent(@PathVariable Long eventId) {
//
//        Event eventToLike = eventService.getEventById(eventId);
//
//        if (eventToLike != null) {
//            eventService.like(eventToLike.getId());
//        }
//
//        return "redirect:/events";
//    }
}