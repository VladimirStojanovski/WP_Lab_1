package mk.finki.ukim.wp.lab.repository;
import mk.finki.ukim.wp.lab.model.EventBooking;
import mk.finki.ukim.wp.lab.bootstrap.DataHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository {

    public List<EventBooking> listAll(){
        return DataHolder.bookings;
    }

    public EventBooking createBooking(String name, String attendee, String address, Long tickets){
        DataHolder.bookings
                .removeIf(eventBooking -> {
                    return eventBooking.getEventName().equals(name)
                            && eventBooking.getAttendeeName().equals(attendee)
                            && eventBooking.getAttendeeAddress().equals(address)
                            && eventBooking.getNumberOfTickets().equals(tickets);
                });
        EventBooking eventBooking = new EventBooking(name, attendee, address, tickets);
        DataHolder.bookings.add(eventBooking);
        return eventBooking;
    }

    public void deleteBooking(String name){
        if(name==null){
            return;
        }
        DataHolder.bookings.removeIf(b->b.getEventName().equals(name));
    }

    public List<EventBooking> filterByUser(String attendeeName) {
        return DataHolder.bookings.stream().filter(r->r.getAttendeeName().equals(attendeeName)).toList();
    }
}
