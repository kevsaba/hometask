package ua.epam.spring.hometask.dao.jdbctemplate.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.dao.EventDAO;
import ua.epam.spring.hometask.dao.UserDAO;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;

@Component
public class TicketRowMapper implements RowMapper<Ticket> {

	@Autowired
	UserDAO userDao;

	@Autowired
	EventDAO eventDao;

	@Override
	public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
		long userId = rs.getLong("UserId");
		User user = userId == 0 ? null : userDao.getById(userId);
		long eventId = rs.getLong("EventId");
		Event event = eventId == 0 ? null : eventDao.getById(eventId);
		Ticket ticket = null;
		if (event != null) {
			ticket = new Ticket(user, event, new java.sql.Timestamp(rs.getDate("DateTime").getTime()).toLocalDateTime(),
					rs.getLong("Seat"));
		}
		return ticket;
	}

}
