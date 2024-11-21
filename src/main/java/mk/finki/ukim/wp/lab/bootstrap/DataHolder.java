package mk.finki.ukim.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.finki.ukim.wp.lab.model.Location;
import mk.finki.ukim.wp.lab.model.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Location> locations = null;
    public static List<Event> events = null;

    // On application startup, initialize the event list
    // On each startup, the list will be initialized with the same values and the previous values will be lost
    @PostConstruct
    public void init() {
        locations = new ArrayList<>();
        locations.add(new Location((long)1, "MSG", "4 Pennsylvania Plaza, NY", "500", "Indoor arena"));
        locations.add(new Location((long)2, "Central Park", "New York, NY 10024", "200", "Outdoor park"));
        locations.add(new Location((long)3, "The Met", "1000 5th Ave, NY 10028", "150", "Modern art gallery"));
        locations.add(new Location((long)4, "Times Square Food Court", "1475 Broadway, NY 10036", "100", "Food court"));
        locations.add(new Location((long)5, "Comedy Cellar", "117 MacDougal St, NY 10012", "300", "Comedy club"));
        locations.add(new Location((long)6, "Javits Center", "655 W 34th St, NY 10001", "250", "Exhibition hall"));
        locations.add(new Location((long)7, "YogaWorks Soho", "459 Broadway, NY 10013", "50", "Yoga studio"));
        locations.add(new Location((long)8, "MetLife Stadium", "1 MetLife Stadium Dr, NJ 07073", "1000", "Open-air venue"));
        locations.add(new Location((long)9, "Bryant Park", "New York, NY 10018", "200", "Outdoor cinema"));
        locations.add(new Location((long)10, "NYU Tandon", "6 Metrotech Center, Brooklyn, NY 11201", "300", "Lecture hall"));

        events = new ArrayList<>();
        events.add(new Event((long)1, "Music Festival", "Enjoy live performances from top artists", 5, locations.get(0)));
        events.add(new Event((long)1, "Music Festival", "Enjoy live performances from top artists", 5, locations.get(1)));
        events.add(new Event((long)2, "Tech Expo", "Explore the latest in tech and innovation", 4, locations.get(2)));
        events.add(new Event((long)3, "Art Gallery Opening", "Experience the launch of a modern art exhibit", 3, locations.get(3)));
        events.add(new Event((long)4, "Food Truck Rally", "Taste amazing dishes from local food trucks", 4, locations.get(4)));
        events.add(new Event((long)5, "Comedy Night", "Laugh out loud with top comedians", 5, locations.get(5)));
        events.add(new Event((long)6, "Book Fair", "Discover new reads and meet authors", 4, locations.get(6)));
        events.add(new Event((long)7, "Yoga Workshop", "Refresh your mind and body with guided yoga", 3, locations.get(7)));
        events.add(new Event((long)8, "Charity Run", "Support a cause while running a fun race", 4, locations.get(8)));
        events.add(new Event((long)9, "Film Screening", "Watch a classic film under the stars", 5, locations.get(9)));
    }
}