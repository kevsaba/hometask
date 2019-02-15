package ua.epam.spring.hometask.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import ua.epam.spring.hometask.domain.Event;

public class EventDaoTest {

	@InjectMocks
	private EventDAO systemUnderTest;

	private LocalDateTime now;
	private Event event;

	
	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		event = new Event();
		event.setName("event1");
		event.addAirDateTime(now);
		event.addAirDateTime(now.plusDays(1));
		event.addAirDateTime(now.plusDays(2));
		
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testSave() {
		
		Event ret = systemUnderTest.save(event);
		
		assertTrue(systemUnderTest.getAll().contains(ret));
	}
	
	@Test
	public void testGetById() {
		
		Event ret = systemUnderTest.save(event);
		
		assertTrue(systemUnderTest.getById(ret.getId()).equals(ret));
	}
	
	@Test
	public void testGetByName() {
		
		Event ret = systemUnderTest.save(event);
		
		assertTrue(systemUnderTest.getByName("event1").equals(ret));
	}
	
	@Test
	public void testGetForDateRange() {
		Set<Event> ret = new HashSet<>();
		Event saved = systemUnderTest.save(event);
		
		ret.addAll(systemUnderTest.getForDateRange(now.toLocalDate(),now.plusDays(5).toLocalDate()));
		
		assertTrue(ret.contains(saved));
	}
	
	@Test
	public void testGetForDateRangeNotIncluded() {
		Set<Event> ret = new HashSet<>();
		Event saved = systemUnderTest.save(event);
		
		ret.addAll(systemUnderTest.getForDateRange(now.plusDays(5).toLocalDate(),now.plusDays(6).toLocalDate()));
		
		assertFalse(ret.contains(saved));
	}

}
