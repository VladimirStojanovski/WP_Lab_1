package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.Category;
import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.repository.jpa.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public EventController(EventService eventService, LocationService locationService, CategoryRepository categoryRepository) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String searchText,
                                @RequestParam(required = false) String minRatingStr,
                                @RequestParam(required = false) String categorySearch,
                                @RequestParam(required = false) String error,
                                Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("categories", categories);

        List<Event> events;

        if (categorySearch != null && !categorySearch.isEmpty()) {
            System.out.println(categorySearch);
            events = eventService.searchEventsByCategory(categorySearch);
        }

         else if ((searchText != null && !searchText.isEmpty()) || (minRatingStr != null && !minRatingStr.isEmpty())){
            double minRating = Double.parseDouble(minRatingStr);
            events = eventService.searchEvents(searchText, minRating);
        } else {
            events = eventService.listAll();
        }

//        if ((searchText != null && !searchText.isEmpty()) || (minRatingStr != null && !minRatingStr.isEmpty())){
//            double minRating = Double.parseDouble(minRatingStr);
//            events = eventService.searchEvents(searchText, minRating);
//        } else {
//            events = eventService.listAll();
//        }
//
//        if ((categoryStr != null && !categoryStr.isEmpty())){
//            events = eventService.searchEventsByCategory(categoryStr);
//        } else {
//            events = eventService.listAll();
//        }

        model.addAttribute("events", events);

        return "listEvents";
    }

    @PostMapping("/add")
    public String saveEvent(@RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId,
                            @RequestParam Long categoryId) {

        Location location = locationService.listAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Category category = categoryRepository.findAll().stream()
                .filter(cat -> cat.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));



        Event event = new Event(name, description, popularityScore, location, category);
        eventService.addEvent(event);

        return "redirect:/events";
    }

    @GetMapping("/add")
    public String addEventPage(Model model) {
        List<Event> events = eventService.listAll();
        List<Location> locations = locationService.listAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("events", events);
        model.addAttribute("locations", locations);
        model.addAttribute("categories", categories);
        return "addEvent";
    }

    @GetMapping("/edit/{eventId}")
    public String getEditEventForm(@PathVariable Long eventId, Model model) {

        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("locations", locationService.listAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addEvent";
    }

    @PostMapping("/edit/{eventId}")
    public String editEvent(@PathVariable Long eventId,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam double popularityScore,
                            @RequestParam Long locationId,
                            @RequestParam Long categoryId) {

        Event event = eventService.getEventById(eventId);

        Location location = locationService.listAll().stream()
                .filter(loc -> loc.getId().equals(locationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid location ID"));

        Category category = categoryRepository.findAll().stream()
                .filter(cat -> cat.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(popularityScore);
        event.setLocation(location);
        event.setCategory(category);

        eventService.addEvent(event);

        return "redirect:/events";
    }

    @GetMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEventById(eventId);
        } catch (IllegalArgumentException e) {
            return "redirect:/events?error=" + e.getMessage();
        }
        return "redirect:/events";
    }

    @GetMapping("/like/{eventId}")
    public String likeEvent(@PathVariable Long eventId) {

        Event eventToLike = eventService.getEventById(eventId);

        if (eventToLike != null) {
            eventService.like(eventToLike.getId());
            eventService.addEvent(eventToLike);
        }

        return "redirect:/events";
    }
}