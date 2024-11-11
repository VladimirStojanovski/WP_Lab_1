package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.EventBooking;
import mk.finki.ukim.wp.lab.service.EventBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventBookingController {

    private final EventBookingService eventBookingService;

    public EventBookingController(EventBookingService eventBookingService) {
        this.eventBookingService = eventBookingService;
    }

    @PostMapping("/eventBooking")
    public String placeBooking(
            @RequestParam String eventName,
            @RequestParam String attendeeName,
            @RequestParam String attendeeAddress,
            @RequestParam int numTickets,
            Model model) {

        EventBooking booking = eventBookingService.placeBooking(eventName, attendeeName, attendeeAddress, numTickets);

        model.addAttribute("booking", booking);

        return "bookingConfirmation";
    }
}
