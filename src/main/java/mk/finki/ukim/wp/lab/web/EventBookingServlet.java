//package mk.finki.ukim.wp.lab.web;
//
//import mk.finki.ukim.wp.lab.model.EventBooking;
//import mk.finki.ukim.wp.lab.service.EventBookingService;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.web.IWebExchange;
//import org.thymeleaf.web.servlet.JakartaServletWebApplication;
//
//import java.io.IOException;
//
//@WebServlet(name = "EventBookingServlet", urlPatterns = "/eventBooking")
//public class EventBookingServlet extends HttpServlet {
//
//    private final EventBookingService eventBookingService;
//    private final SpringTemplateEngine springTemplateEngine;
//
//    public EventBookingServlet(EventBookingService eventBookingService, SpringTemplateEngine springTemplateEngine) {
//        this.eventBookingService = eventBookingService;
//        this.springTemplateEngine = springTemplateEngine;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        IWebExchange webExchange = JakartaServletWebApplication
//                .buildApplication(getServletContext())
//                .buildExchange(req, resp);
//
//        WebContext context = new WebContext(webExchange);
//
//        String attendeeName = req.getLocalName();
//        String attendeeAddress = req.getRemoteAddr();
//        String eventName = req.getParameter("eventName");
//        String numTicketsParam = req.getParameter("numTickets");
//
//        int numberOfTickets;
//        try {
//            numberOfTickets = Integer.parseInt(numTicketsParam);
//        } catch (NumberFormatException e) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number of tickets");
//            return;
//        }
//
//        EventBooking booking = eventBookingService.placeBooking(eventName, attendeeName, attendeeAddress, numberOfTickets);
//
//        context.setVariable("booking", booking);
//
//        springTemplateEngine.process("bookingConfirmation", context, resp.getWriter());
//
//    }
//}