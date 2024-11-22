package mk.finki.ukim.wp.lab.web.controller;

import mk.finki.ukim.wp.lab.model.EventBooking;
import mk.finki.ukim.wp.lab.service.EventBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {

    private final EventBookingService eventBookingService;

    public EventBookingController(EventBookingService eventBookingService) {
        this.eventBookingService = eventBookingService;
    }

    @PostMapping("/placeBooking")
    public String placeBooking(
            @RequestParam String eventName,
            @RequestParam int numTickets,
            Model model) {

        String attendeeName = "asdasdasd";
        String attendeeAddress = "asdasdasd@gmail.com";

        EventBooking booking = eventBookingService.placeBooking(eventName, attendeeName, attendeeAddress, numTickets);

        model.addAttribute("booking", booking);

        return "bookingConfirmation";
    }
}
