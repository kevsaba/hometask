package ua.epam.spring.hometask.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.manager.TenTicketDiscountStrategy;

@RunWith(MockitoJUnitRunner.class)
public class TenTicketDiscountStrategyTest {

	@Test
	public void testGetDiscountTenTicket() {
		TenTicketDiscountStrategy systemUnderTest = new TenTicketDiscountStrategy();
		Event event = new Event();
		User user = new User();
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.now(), 10);
		assertTrue(ret == 50);
	}

	
	@Test
	public void testGetDiscountNineTicket() {
		TenTicketDiscountStrategy systemUnderTest = new TenTicketDiscountStrategy();
		Event event = new Event();
		User user = new User();
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.now(), 9);
		assertTrue(ret == 0);
	}
	
	@Test
	public void testGetDiscountUserMod10Ticket() {
		TenTicketDiscountStrategy systemUnderTest = new TenTicketDiscountStrategy();
		Event event = new Event();
		User user = new User();
		Set<Ticket> tickets = new HashSet<>();
		//#9
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		
		user.addTickets(tickets);
		
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.now(), 1);
		assertTrue(ret == 50);
	}
	
	
	@Test
	public void testGetDiscountUserMod10Ticket2() {
		TenTicketDiscountStrategy systemUnderTest = new TenTicketDiscountStrategy();
		Event event = new Event();
		User user = new User();
		Set<Ticket> tickets = new HashSet<>();
		//#9
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		tickets.add(mock(Ticket.class));
		
		user.addTickets(tickets);
		
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.now(), 2);
		assertTrue(ret == 50);
	}
}
