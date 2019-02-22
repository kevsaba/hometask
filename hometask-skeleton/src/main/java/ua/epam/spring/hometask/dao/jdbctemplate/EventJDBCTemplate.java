package ua.epam.spring.hometask.dao.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.EventRowMapper;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;

public class EventJDBCTemplate {

	@Autowired
	private AirDateJDBCTemplate airDateJDBCTemplate;

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public EventJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public Event save(Event event) {
		String SQL = "insert into event (Name,BasePrice,Raiting) values (?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "Name", "BasePrice", "Raiting" });
				ps.setString(1, event.getName());
				ps.setDouble(2, event.getBasePrice());
				ps.setString(3, event.getRating() == null ? EventRating.LOW.toString() : event.getRating().toString());
				return ps;
			}
		}, keyHolder);

		airDateJDBCTemplate.save(keyHolder.getKey().longValue(), event.getAuditoriums());
		event.setId(keyHolder.getKey().longValue());
		return event;
	}

	public void remove(Event event) {
		String SQL = "delete from event where id = ?";
		airDateJDBCTemplate.remove(event.getId());
		jdbcTemplateObject.update(SQL, event.getId());
		System.out.println("Deleted Record with ID = " + event.getId());
	}

	public Event getById(Long id) {
		String SQL = "select * from event where id = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new EventRowMapper(airDateJDBCTemplate));
		return event;
	}

	public Set<Event> getAll() {
		String SQL = "select * from event";
		Set<Event> events = new HashSet<>(jdbcTemplateObject.query(SQL, new EventRowMapper(airDateJDBCTemplate)));
		return events;
	}

	public Event getByName(String name) {
		String SQL = "select * from event where name = ?";
		Event event = jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new EventRowMapper(airDateJDBCTemplate));
		return event;
	}

	public Set<Event> getForDateRange(LocalDate from, LocalDate to) {
		return airDateJDBCTemplate.getEventsAirDateBetween(from, to);
	}

}
