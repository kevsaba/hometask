package ua.epam.spring.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import ua.epam.spring.exceptions.TicketValidationException;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserService;
import javax.annotation.PostConstruct;

@Component
public class Application {
	
	@Autowired
	UserService userServiceImpl ;
	@Autowired
	EventService eventService ;
	@Autowired
	AuditoriumService auditoriumService ;
	@Autowired
	BookingService bookingService ;
	
	@PostConstruct
	public void start() {
		LocalDateTime localDateTime1 = LocalDateTime.of(LocalDate.of(2019,Month.JANUARY,1), LocalTime.of(12,10));
		
		User u1 = new User();
		u1.setFirstName("k1");
		u1.setLastName("last");
		u1.setEmail("email1");
		u1.setBirthday(localDateTime1);
		userServiceImpl.save(u1);
		
		User u2 = new User();
		u2.setFirstName("k2");
		u2.setLastName("last2");
		u2.setEmail("email2");
		u1.setBirthday(localDateTime1);
		userServiceImpl.save(u2);
		
		//System.out.println("++++++++++++++++++++++++++++++++++" + userServiceImpl.getUserByEmail("email1").getFirstName());
		
		Event e1 = new Event();
		e1.setName("e1");
		NavigableSet<LocalDateTime> airDates = new TreeSet<>() ;
		airDates.add( localDateTime1);
		airDates.add( LocalDateTime.of(LocalDate.of(2019,Month.JANUARY,2), LocalTime.of(12,10)));
		airDates.add( LocalDateTime.of(LocalDate.of(2019,Month.JANUARY,3), LocalTime.of(12,10)));
		airDates.add( LocalDateTime.of(LocalDate.of(2019,Month.JANUARY,4), LocalTime.of(12,10)));
		e1.setAirDates(airDates );
		e1.setBasePrice(5.0);
		e1.assignAuditorium(localDateTime1,  auditoriumService.getByName("auditorium1"));
	
		eventService.save(e1);
		eventService.getForDateRange(LocalDate.of(2019,Month.JANUARY,2), LocalDate.of(2019,Month.JANUARY,4));
		
		//System.out.println("+++++++EVENT ID+++++++++++++++++++++++++++" + eventService.getByName("e1").getId());
		//System.out.println("++++++++++++++++++++++++++++++++++" + auditoriumService.getByName("auditorium1").getName());
		
		Set<Ticket> tickets = new HashSet<>();
		LocalDateTime now = LocalDateTime.now();
		Ticket ticket = new Ticket(u1, e1, localDateTime1, 4);
		Ticket ticket2 = new Ticket(u2, e1, localDateTime1, 4);
		tickets.add(ticket);
		tickets.add(ticket2);
		try {
			bookingService.bookTickets(tickets);
		} catch (TicketValidationException e) {
			System.out.println("Error happened while booking tickets: " + e.getMessage());
		}
		bookingService.getPurchasedTicketsForEvent(e1, localDateTime1);
		
		Set<Long> seats = new HashSet<>();
		seats.add(4l);
		seats.add(5l);
		seats.add(10l);
		
		bookingService.getTicketsPrice(e1, localDateTime1, u1, seats);
		
		//System.out.println("++++++++PURCHASED TICKETS++++++++++++++++++"+ bookingService.getPurchasedTicketsForEvent(e1, now));
		
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	}


}
