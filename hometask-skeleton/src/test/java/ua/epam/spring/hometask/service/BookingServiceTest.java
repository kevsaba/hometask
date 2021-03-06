package ua.epam.spring.hometask.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import ua.epam.spring.exceptions.TicketValidationException;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.impl.BookingServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

	
	@Mock
	private TicketDAO ticketDao;
	
	@Mock
	private UserDAO userDao;
	
	@Mock
	private DiscountService discountService;
	
	@InjectMocks
	private BookingServiceImpl systemUnderTest;

	private static Set<Ticket> tickets;
	private Event event;
	private LocalDateTime now;
	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		
		Auditorium auditorium = new Auditorium();
		auditorium.setName("au1");
		auditorium.setNumberOfSeats(10);
		Set<Long> vipSeats = new HashSet<>();
		vipSeats.add(4l);
		vipSeats.add(5l);
		auditorium.setVipSeats(vipSeats);
		NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
		auditoriums.put(now, auditorium);
		
		event = new Event();
		event.setId(1l);
		event.setName("event1");
		event.addAirDateTime(now);
		event.addAirDateTime(now.plusDays(1));
		event.addAirDateTime(now.plusDays(2));
		event.setAuditoriums(auditoriums);		
		event.setRating(EventRating.LOW);
		event.setBasePrice(5.0);
		
		MockitoAnnotations.initMocks(this);

	}
	
	@Test
	public void testGetTicketsPriceNormal() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);

		event.setRating(EventRating.LOW);
		event.setBasePrice(5.0);
		
		Set<Long> seats = new HashSet<>();
		seats.add(1l);
		seats.add(2l);
		
		double ret = systemUnderTest.getTicketsPrice(event,now,u1,seats);
		assertTrue(ret == 10.0 );
		assertTrue(u1.getTickets().iterator().next().getPrice()==5.00);
	}
	
	@Test
	public void testGetTicketsPriceVIP() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);

		event.setRating(EventRating.LOW);
		event.setBasePrice(5.0);
		
		Set<Long> seats = new HashSet<>();
		seats.add(4l);
		seats.add(5l);
		
		double ret = systemUnderTest.getTicketsPrice(event,now,u1,seats);
		assertTrue(ret == 20.0 );
		assertTrue(u1.getTickets().iterator().next().getPrice()==10.00);
	}
	
	@Test
	public void testGetTicketsPriceBday() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);
		u1.setBirthday(now);
		
		event.setRating(EventRating.LOW);
		event.setBasePrice(5.0);
		
		Set<Long> seats = new HashSet<>();
		seats.add(1l);
		seats.add(2l);
		when(discountService.getDiscount(u1, event, now, 2)).thenReturn((byte) 5);		
		
		double ret = systemUnderTest.getTicketsPrice(event,now,u1,seats);
		assertTrue(ret == 9.75 );
		assertTrue(u1.getTickets().iterator().next().getPrice()==4.75);
	}
	
	@Test
	public void testGetTicketsPriceTenthTicket() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);
		u1.setBirthday(now);
		
		event.setRating(EventRating.LOW);
		event.setBasePrice(5.0);
		
		Set<Long> seats = new HashSet<>();
		seats.add(1l);
		seats.add(2l);
		when(discountService.getDiscount(u1, event, now, 2)).thenReturn((byte) 50);		
		
		double ret = systemUnderTest.getTicketsPrice(event,now,u1,seats);
		assertTrue(ret == 7.50 );
		assertTrue(u1.getTickets().iterator().next().getPrice()==2.50);
	}
	
	@Test
	public void testGetTicketsPriceNormalHigh() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);

		event.setRating(EventRating.HIGH);
		event.setBasePrice(5.0);
		
		Set<Long> seats = new HashSet<>();
		seats.add(1l);
		seats.add(2l);
		
		double ret = systemUnderTest.getTicketsPrice(event,now,u1,seats);
		assertTrue(ret == 12.0 );
		assertTrue(u1.getTickets().iterator().next().getPrice()==6.00);
		
	}
	
	@Test
	public void testGetPurchasedTicketsForEvent() throws TicketValidationException {
		User u1 = new User();
		u1.setId(1l);
		List<User> users = new ArrayList<User>(List.of(u1));
		when(userDao.getAll()).thenReturn(users);
		when(userDao.getById(u1.getId())).thenReturn(u1);
		tickets= new HashSet<>();
		Ticket t1 = new Ticket(u1,event,now,1l);
		tickets.add(t1);
		when(ticketDao.getAllTickets()).thenReturn(tickets);
		
		
		systemUnderTest.bookTickets(tickets);
		Set<Ticket> purchasedTickets =systemUnderTest.getPurchasedTicketsForEvent(event, now);
		
		assertFalse(purchasedTickets.isEmpty());
		assertTrue(purchasedTickets.contains(t1));
		assertTrue(ticketDao.getAllTickets().contains(t1));
	}
	
	@Test
	public void testGetPurchasedTicketsForEventTicketNotInTime() throws TicketValidationException {
		tickets= new HashSet<>();
		Ticket t1 = new Ticket(mock(User.class),event, now,1l);
		tickets.add(t1);
		
		systemUnderTest.bookTickets(tickets);
		Set<Ticket> purchasedTickets =systemUnderTest.getPurchasedTicketsForEvent(event, now);
		
		assertTrue(purchasedTickets.isEmpty());
		assertFalse(purchasedTickets.contains(t1));
	}

}
