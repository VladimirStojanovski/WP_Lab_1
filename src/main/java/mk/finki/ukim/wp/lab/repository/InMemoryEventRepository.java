//package mk.finki.ukim.wp.lab.repository;
//
//import mk.finki.ukim.wp.lab.model.Event;
//import mk.finki.ukim.wp.lab.bootstrap.DataHolder;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//public class InMemoryEventRepository {
//
//    public List<Event> findAll() {
//        return DataHolder.events;
//    }
//
//    public Event findById(Long id) {
//        return DataHolder.events.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public void like(long id) {
//        for (Event event : DataHolder.events) {
//            if (event.getId() == id && !event.isLiked()) {
//                event.setPopularityScore(event.getPopularityScore() + 1);
//                event.setLiked(true);
//            }
//        }
//    }
//
//    public List<Event> searchEvents(String text, Double minRating) {
//        return DataHolder.events.stream()
//                .filter(event -> (event.getName().contains(text) || event.getDescription().contains(text)) &&
//                        event.getPopularityScore() >= minRating)
//                .collect(Collectors.toList());
//    }
//
//    public Event save(Event event) {
//        DataHolder.events.removeIf(c -> c.getName().equals(event.getName()));
//
//        DataHolder.events.add(event);
//
//        return event;
//    }
//
//}