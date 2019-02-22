package ua.epam.spring.hometask.dao.jdbctemplate.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.epam.spring.hometask.dao.jdbctemplate.AirDateJDBCTemplate;
import ua.epam.spring.hometask.domain.Event;

public class EventRowMapper implements RowMapper<Event> {

	AirDateJDBCTemplate airDateJDBCTemplate;

	public EventRowMapper(AirDateJDBCTemplate airDateJDBCTemplate) {
		this.airDateJDBCTemplate = airDateJDBCTemplate;
	}

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Event event = new Event();

		event.setId(rs.getLong("ID"));
		event.setBasePrice(rs.getDouble("BasePrice"));
		event.setName(rs.getString("Name"));
		event.setAuditoriums(airDateJDBCTemplate.getAuditoriumsByEventId(event.getId()));
		event.setAirDates(airDateJDBCTemplate.getAirDatesByEventId(event.getId()));

		return event;
	}

}
