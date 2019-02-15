package ua.epam.spring.hometask.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.EventService;
@Component
public class EventServiceImpl implements EventService{

	@Autowired
	private EventDAO eventDao; 

	@Override
	public Event save(Event event) {
		return eventDao.save(event);
	}

	@Override
	public void remove(Event event) {
		eventDao.remove(event);
	}

	@Override
	public Event getById(Long id) {
		return eventDao.getById(id);
	}

	@Override
	public Collection<Event> getAll() {
		return eventDao.getAll();
	}

	@Override
	public Event getByName(String name) {
		return eventDao.getByName(name);
	}
	
	@Override
	public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
		return eventDao.getForDateRange(from,to);
	}

	@Override
	public Set<Event> getNextEvents(LocalDateTime to) {
		return eventDao.getForDateRange(LocalDate.now(),to.toLocalDate());
	}

}
