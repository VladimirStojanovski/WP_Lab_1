package mk.finki.ukim.wp.lab.service.impl;

import mk.finki.ukim.wp.lab.model.EventBooking;
import mk.finki.ukim.wp.lab.repository.BookingRepository;
import mk.finki.ukim.wp.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingServiceImpl implements EventBookingService {

    public BookingRepository eventBookingRepository;

    public EventBookingServiceImpl(BookingRepository eventBookingRepository) {
        this.eventBookingRepository = eventBookingRepository;
    }

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, int numberOfTickets) {
        return eventBookingRepository.createBooking(eventName, attendeeName, attendeeAddress, (long) numberOfTickets);
    }

    @Override
    public List<EventBooking> listAll() {
        return eventBookingRepository.listAll();
    }

    @Override
    public List<EventBooking> filterByUser(String attendeeName) {
        return eventBookingRepository.filterByUser(attendeeName);
    }

}