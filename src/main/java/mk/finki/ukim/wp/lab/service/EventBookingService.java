package mk.finki.ukim.wp.lab.service;

import mk.finki.ukim.wp.lab.model.EventBooking;

import java.util.List;

public interface EventBookingService {
    EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets);
    List<EventBooking> listAll();
    List<EventBooking> filterByUser(String attendeeName);
}