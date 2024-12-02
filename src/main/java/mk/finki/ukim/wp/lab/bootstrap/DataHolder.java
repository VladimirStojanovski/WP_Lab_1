package mk.finki.ukim.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.wp.lab.model.Category;
import mk.finki.ukim.wp.lab.model.EventBooking;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.repository.jpa.CategoryRepository;
import mk.finki.ukim.wp.lab.repository.jpa.EventRepository;
import mk.finki.ukim.wp.lab.repository.jpa.LocationRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Location> locations = null;
    public static List<Event> events = null;
    public static List<EventBooking> bookings = null;
    public static List<Category> categories = null;

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public DataHolder(EventRepository eventRepository, LocationRepository locationRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
    }

    // On application startup, initialize the event list
    // On each startup, the list will be initialized with the same values and the previous values will be lost
    @PostConstruct
    public void init() {

        categories=new ArrayList<>();
        if (this.categoryRepository.count() == 0) {
            categories.add(new Category("sports event"));
            categories.add(new Category("social event"));
            categories.add(new Category("entertainment event"));
            categoryRepository.saveAll(categories);
        }

        locations = new ArrayList<>();
        if (this.locationRepository.count() == 0) {
            locations.add(new Location("MSG", "4 Pennsylvania Plaza, NY", "500", "Indoor arena"));
            locations.add(new Location("Central Park", "New York, NY 10024", "200", "Outdoor park"));
            locations.add(new Location("The Met", "1000 5th Ave, NY 10028", "150", "Modern art gallery"));
            locations.add(new Location("Times Square Food Court", "1475 Broadway, NY 10036", "100", "Food court"));
            locations.add(new Location("Comedy Cellar", "117 MacDougal St, NY 10012", "300", "Comedy club"));
            locations.add(new Location("Javits Center", "655 W 34th St, NY 10001", "250", "Exhibition hall"));
            locations.add(new Location("YogaWorks Soho", "459 Broadway, NY 10013", "50", "Yoga studio"));
            locations.add(new Location("MetLife Stadium", "1 MetLife Stadium Dr, NJ 07073", "1000", "Open-air venue"));
            locations.add(new Location("Bryant Park", "New York, NY 10018", "200", "Outdoor cinema"));
            locations.add(new Location("NYU Tandon", "6 Metrotech Center, Brooklyn, NY 11201", "300", "Lecture hall"));
            locationRepository.saveAll(locations);
        }

        events = new ArrayList<>();
        if (this.eventRepository.count() == 0) {
            events.add(new Event("Music Festival", "Enjoy live performances from top artists", 5, locations.get(0), categories.get(0)));
            events.add(new Event("Music Festival", "Enjoy live performances from top artists", 5, locations.get(1), categories.get(0)));
            events.add(new Event("Tech Expo", "Explore the latest in tech and innovation", 4, locations.get(2), categories.get(0)));
            events.add(new Event("Art Gallery Opening", "Experience the launch of a modern art exhibit", 3, locations.get(3), categories.get(1)));
            events.add(new Event("Food Truck Rally", "Taste amazing dishes from local food trucks", 4, locations.get(4), categories.get(1)));
            events.add(new Event("Comedy Night", "Laugh out loud with top comedians", 5, locations.get(5), categories.get(1)));
            events.add(new Event("Book Fair", "Discover new reads and meet authors", 4, locations.get(6), categories.get(2)));
            events.add(new Event("Yoga Workshop", "Refresh your mind and body with guided yoga", 3, locations.get(7), categories.get(2)));
            events.add(new Event("Charity Run", "Support a cause while running a fun race", 4, locations.get(8), categories.get(2)));
            events.add(new Event("Film Screening", "Watch a classic film under the stars", 5, locations.get(9), categories.get(2)));
            eventRepository.saveAll(events);
        }

        bookings=new ArrayList<>();
    }
}