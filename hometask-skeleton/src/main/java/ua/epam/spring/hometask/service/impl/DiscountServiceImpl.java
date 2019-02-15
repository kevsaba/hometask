package ua.epam.spring.hometask.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountService;
import ua.epam.spring.hometask.service.DiscountStrategy;

public class DiscountServiceImpl implements DiscountService{

	private List<DiscountStrategy> discountStrategies;
	private byte discount = 0 ;
	
	@Autowired
	public DiscountServiceImpl(List<DiscountStrategy> discountStrategies) {
		this.discountStrategies = discountStrategies;
	}
	
	@Override
	public byte getDiscount(User user, Event event, LocalDateTime airDateTime, long numberOfTickets) {
		discountStrategies.stream().forEach(ds-> {
			byte discountCalculated = ds.getDiscount(user, event, airDateTime, numberOfTickets);
			if(discount<discountCalculated) {
				discount=discountCalculated;
			}
		});
		return discount;
	}
	
}
