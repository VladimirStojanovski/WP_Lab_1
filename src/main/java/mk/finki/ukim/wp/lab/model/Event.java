package mk.finki.ukim.wp.lab.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double popularityScore;
    private boolean isLiked;

    @ManyToOne
    private Location location;

    @ManyToOne
    private Category category;

    public Event(String name, String description, double popularityScore, Location location, Category category){
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
        this.category = category;
    }
}