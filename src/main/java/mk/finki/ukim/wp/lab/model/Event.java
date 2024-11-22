package mk.finki.ukim.wp.lab.model;

import lombok.Data;

@Data
public class Event {
    private Location location;
    private Long id;
    private String name;
    private String description;
    private double popularityScore;

    private boolean isLiked;

    public Event(String name, String description, double popularityScore, Location location){
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
    }
}