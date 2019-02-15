package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
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
	public double getTicketsPrice(Event event, LocalDateTime dateTime, User user, Set<Long> seats) {
		int amountOfVipSeats = event.getAuditoriums().get(dateTime).getVipSeats().stream().filter(vs-> seats.contains(vs)).collect(Collectors.toList()).size();
		return getTicketPrice(event, dateTime, user, seats.size(), amountOfVipSeats);
	}
	

	@Override
	public void bookTickets(Set<Ticket> tickets) throws TicketValidationException {
		if(ValidateTicket.ticketValidation(tickets)) {
		tickets.stream().forEach(t-> t.setPrice(getIndividualTicketPrice(t.getEvent(), t.getDateTime(),t.getUser(), t.getSeat())));
		ticketDao.save(tickets);
		ticketDao.getAllTickets().stream().filter(t->userDao.getAll().contains(t.getUser())).forEach(t->updateUser(t));
		}
	}

	@Override
	public Set<Ticket> getPurchasedTicketsForEvent(Event event, LocalDateTime dateTime) {
		return ticketDao.getAllTickets().stream().filter(t->t.getEvent().equals(event) && t.getDateTime().equals(dateTime)).collect(Collectors.toSet());
	}
	
	
	private void updateUser(Ticket t) {
		User user = userDao.getById(t.getUser().getId());
		user.addTicket(t);
		userDao.save(user);
	}


	public double getIndividualTicketPrice(Event event, LocalDateTime dateTime, User user, Long seat) {
		int amountOfVipSeats = event.getAuditoriums().get(dateTime).getVipSeats().contains(seat)?1:0;
		return getTicketPrice(event, dateTime, user, 1, amountOfVipSeats);
	}
	
	private double getTicketPrice(Event event, LocalDateTime dateTime, User user, int amountOfSeats,int amountOfVipSeats) {
		byte discount  = discountService.getDiscount(user, event, dateTime, amountOfSeats);
		double eventRaitinPrice= (EventRating.HIGH.equals(event.getRating())?event.getBasePrice()*1.2:event.getBasePrice());
		return calculatePriceOfTicket(eventRaitinPrice, amountOfSeats, amountOfVipSeats,discount);
	}
	
	public void setTicketDao(TicketDAO ticketDao) {
		this.ticketDao = ticketDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}
	
	public double calculatePriceOfTicket(double eventRaitinPrice, int amountOfSeats, int amountOfVipSeats, byte discount) {
		double seatWithDiscount = 0.0;
		if(discount!=0) {
			if(amountOfVipSeats>0 ) {
				--amountOfVipSeats;
			}else {
				--amountOfSeats;
			}
			double discountApplied = eventRaitinPrice - (eventRaitinPrice * ( discount * 0.01));
			seatWithDiscount = discountApplied;
		}
		double vipPrice = (eventRaitinPrice * amountOfVipSeats)*2;
		double normalPrice = (eventRaitinPrice * (amountOfSeats - amountOfVipSeats));
		return vipPrice+normalPrice+seatWithDiscount;
	}
}
