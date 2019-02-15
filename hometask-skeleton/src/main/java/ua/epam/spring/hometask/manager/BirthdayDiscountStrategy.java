package ua.epam.spring.hometask.manager;

import java.time.LocalDateTime;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountStrategy;

public class BirthdayDiscountStrategy implements DiscountStrategy{

	@Override
	public byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		return (byte) ( Math.abs(airDateTime.getDayOfYear()-user.getBirthday().getDayOfYear())<5?5:0);
	}

}
