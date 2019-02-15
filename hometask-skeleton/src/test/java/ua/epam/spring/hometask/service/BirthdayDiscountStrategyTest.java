package ua.epam.spring.hometask.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.manager.BirthdayDiscountStrategy;

public class BirthdayDiscountStrategyTest {

	@Test
	public void testGetDiscountBdayDay() {
		BirthdayDiscountStrategy systemUnderTest = new BirthdayDiscountStrategy();
		Event event = new Event();
		User user = new User();
		LocalDateTime time = LocalDateTime.now();
		user.setBirthday(time);
		
		byte ret = systemUnderTest.getDiscount(user, event, time, 1);
		assertTrue(ret == 5);
	}

	@Test
	public void testGetDiscountBdayOtherDay() {
		BirthdayDiscountStrategy systemUnderTest = new BirthdayDiscountStrategy();
		Event event = new Event();
		User user = new User();
		LocalDateTime time = LocalDateTime.now();
		user.setBirthday(time);
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.now(), 1);
		assertTrue(ret == 5);
	}
	
	@Test
	public void testGetDiscountBdayDayNot() {
		BirthdayDiscountStrategy systemUnderTest = new BirthdayDiscountStrategy();
		Event event = new Event();
		User user = new User();
		LocalDateTime time = LocalDateTime.now();
		user.setBirthday(time);
		
		byte ret = systemUnderTest.getDiscount(user, event, LocalDateTime.of(LocalDate.of(1992, 1, 1), time.toLocalTime()), 1);
		assertTrue(ret == 0);
	}
}
