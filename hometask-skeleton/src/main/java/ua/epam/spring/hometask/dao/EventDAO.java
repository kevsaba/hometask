package ua.epam.spring.hometask.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.domain.Event;
@Repository
public class EventDAO {
	
	private static Map<Long,Event> events= new HashMap<>();
	private Long idGenerated = 0L;

	public Event save(Event event) {
		event.setId(++idGenerated);
		events.put(event.getId(), event);
		return event;
	}

	public void remove(Event event) {
		events.remove(event.getId());
	}

	public Event getById(Long id) {
		return events.get(id);
	}

	public Collection<Event> getAll() {
		return events.values();
	}

	public Event getByName(String name) {
		 return events.values().stream().filter(u->u.getName().equals(name)).findFirst().get();
	}

	public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
		Set<Event> eventsInBetween = new HashSet<>();
		for(Event event : events.values()){
			if (event.getAirDates().stream().anyMatch(dates-> dates.toLocalDate().isAfter(from) && dates.toLocalDate().isBefore(to))) {
				eventsInBetween.add(event);
			}
		}
		return eventsInBetween;
	}
	
	

}
