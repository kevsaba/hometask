package ua.epam.spring.hometask.manager;

import java.time.LocalDateTime;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountStrategy;

public class TenTicketDiscountStrategy implements DiscountStrategy{

	@Override
	public byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		return (byte) (10-(user.getTickets().size() % 10) <= numberOfTickets?50:0) ;
	}

}
