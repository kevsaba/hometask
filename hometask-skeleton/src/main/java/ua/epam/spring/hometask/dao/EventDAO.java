package ua.epam.spring.hometask.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.jdbctemplate.EventJDBCTemplate;
import ua.epam.spring.hometask.domain.Event;

@Repository
public class EventDAO {

	@Autowired
	EventJDBCTemplate eventJDBCTemplate;

	public Event save(Event event) {
		return eventJDBCTemplate.save(event);
	}

	public void remove(Event event) {
		eventJDBCTemplate.remove(event);
	}

	public Event getById(Long id) {
		return eventJDBCTemplate.getById(id);
	}

	public Collection<Event> getAll() {
		return eventJDBCTemplate.getAll();
	}

	public Event getByName(String name) {
		return eventJDBCTemplate.getByName(name);
	}

	public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
		return eventJDBCTemplate.getForDateRange(from, to);
	}

}
