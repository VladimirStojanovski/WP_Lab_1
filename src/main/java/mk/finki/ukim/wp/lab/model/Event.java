package mk.finki.ukim.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Event {
    private Location location;
    private Long id;
    private String name;
    private String description;
    private double popularityScore;

    public Event(Long id, String name, String description, double popularityScore){
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;

    }
}