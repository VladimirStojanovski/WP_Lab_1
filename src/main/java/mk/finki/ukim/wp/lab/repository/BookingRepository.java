package mk.finki.ukim.wp.lab.repository;
import mk.finki.ukim.wp.lab.model.EventBooking;

import java.util.ArrayList;
import java.util.List;


public class BookingRepository {
    private List<EventBooking> bookings;

    public BookingRepository() {
        this.bookings = new ArrayList<>();

        bookings.add(new EventBooking("Asdasd", "asdasd", "asdasd", (long)8));

        // List all bookings + search filter for each attribute - extra req
    }
}
