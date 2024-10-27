package mk.finki.ukim.wp.lab.web;

import mk.finki.ukim.wp.lab.model.Event;
import mk.finki.ukim.wp.lab.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventListServlet", urlPatterns = "")
public class EventListServlet extends HttpServlet {

    private final EventService eventService;
    private final SpringTemplateEngine springTemplateEngine;

    public EventListServlet(SpringTemplateEngine springTemplateEngine, EventService eventService) {
        this.eventService = eventService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String searchText = req.getParameter("searchText");
        String minRatingStr = req.getParameter("minRating");

        System.out.println(searchText);
        System.out.println(minRatingStr);

        List<Event> events;

        if (searchText != null && minRatingStr != null) {
            double minRating = Double.parseDouble(minRatingStr);
            events = eventService.searchEvents(searchText, minRating);
        } else {
            events = eventService.listAll();
        }

        context.setVariable("events", events);

        springTemplateEngine.process("listEvents", context, resp.getWriter());
    }
}