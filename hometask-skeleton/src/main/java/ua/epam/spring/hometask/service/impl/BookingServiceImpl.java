package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.epam.spring.exceptions.TicketValidationException;
import ua.epam.spring.exceptions.ValidateTicket;
import ua.epam.spring.hometask.dao.TicketDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.DiscountService;
@Component
public class BookingServiceImpl implements BookingService {

@Autowired
private TicketDAO ticketDao;
@Autowired
private UserDAO userDao;
@Autowired
private DiscountService discountService;

	@Override
	public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) throws TicketValidationException {
		return createAndBookTickets(event,dateTime,user,seats);
	}
	

	private  double createAndBookTickets(Event event, LocalDateTime dateTime, User user, Set<Long> seats) throws TicketValidationException {
		Set<Ticket> tickets = new HashSet<>();
		byte discount  = discountService.getDiscount(user, event, dateTime, seats.size());
		double eventRaitingPrice= (EventRating.HIGH.equals(event.getRating())?event.getBasePrice()*1.2:event.getBasePrice());
		boolean discountApplied = false;
		double totalPrice = 0.0;
		
		for(Long seat : seats) {
		Ticket ticket = new Ticket(user, event, dateTime, seat);
		ticket.setPrice(calculatePriceOf1Ticket(eventRaitingPrice, event.getAuditoriums().get(dateTime).getVipSeats().contains(seat), !discountApplied? discount:0));
		updateUser(ticket);
		if(!discountApplied && discount == 100) {
			ticket.isLuckyTicket();
		}
		tickets.add(ticket);
		discountApplied = true;
		totalPrice = totalPrice + ticket.getPrice();
		}
		bookTickets(tickets);
		return totalPrice;
	}

	@Override
	public void bookTickets(Set<Ticket> tickets) throws TicketValidationException {
		if(ValidateTicket.ticketValidation(tickets)) {
		ticketDao.save(tickets);
		}
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		return ticketDao.getAllTickets().stream().filter(t->t.getEvent().equals(event) && t.getDateTime().equals(dateTime)).collect(Collectors.toSet());
	}
	
	
	private void updateUser(Ticket t) {
		User user = userDao.getById(t.getUser().getId());
		if(user!=null) {
		user.addTicket(t);
		userDao.save(user);
		}
	}

	public double calculatePriceOf1Ticket(double eventRaitingPrice, boolean isVIP, byte discount) {
		double priceWithDiscount = eventRaitingPrice - (eventRaitingPrice * ( discount * 0.01));
		if(isVIP) {
			return priceWithDiscount*2;
		}else {
			return priceWithDiscount;
		}
	}
}
