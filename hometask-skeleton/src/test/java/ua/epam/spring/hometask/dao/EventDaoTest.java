package ua.epam.spring.hometask.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ua.epam.spring.hometask.dao.jdbctemplate.EventJDBCTemplate;
import ua.epam.spring.hometask.domain.Event;

public class EventDaoTest {

	@Mock
	private EventJDBCTemplate eventJDBCTemplate;

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

		Event eventId = event;
		eventId.setId(1l);
		Mockito.when(eventJDBCTemplate.save(event)).thenReturn(eventId);

		Event ret = systemUnderTest.save(event);

		assertTrue(ret.getId() != null);
	}

	@Test
	public void testGetById() {
		Event eventId = event;
		eventId.setId(1l);
		Mockito.when(eventJDBCTemplate.save(event)).thenReturn(eventId);
		Mockito.when(eventJDBCTemplate.getById(event.getId())).thenReturn(eventId);
		Event ret = systemUnderTest.save(event);

		assertTrue(systemUnderTest.getById(ret.getId()).equals(ret));
	}

	@Test
	public void testGetByName() {

		Mockito.when(eventJDBCTemplate.getByName(event.getName())).thenReturn(event);

		assertTrue(systemUnderTest.getByName("event1").equals(event));
	}

	@Test
	public void testGetForDateRange() {
		Set<Event> ret = new HashSet<>();

		Mockito.when(eventJDBCTemplate.getForDateRange(now.toLocalDate(), now.plusDays(5).toLocalDate())).thenReturn(Set.of(event));
		ret.addAll(systemUnderTest.getForDateRange(now.toLocalDate(), now.plusDays(5).toLocalDate()));

		assertTrue(ret.contains(event));
	}

	@Test
	public void testGetForDateRangeNotIncluded() {
		Set<Event> ret = new HashSet<>();
		Event saved = systemUnderTest.save(event);

		ret.addAll(systemUnderTest.getForDateRange(now.plusDays(5).toLocalDate(), now.plusDays(6).toLocalDate()));

		assertFalse(ret.contains(saved));
	}

}
