package ua.epam.spring.hometask.dao.jdbctemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.AirDateAuditoriumRowMapper;
import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.AirDateRowMapper;
import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.EventRowMapper;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;

public class AirDateJDBCTemplate {

	@Autowired
	private AirDateRowMapper airDateRowMapper;

	@Autowired
	private AirDateAuditoriumRowMapper airdateAuditoriumRowMapper;

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public AirDateJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public NavigableSet<LocalDateTime> getAirDatesByEventId(Long id) {
		String SQL = "select * from airdate where EventId  = ?";
		NavigableSet<LocalDateTime> events = new TreeSet<>(jdbcTemplateObject.query(SQL, new Object[] { id }, airDateRowMapper));
		return events;
	}

	public Set<Event> getEventsAirDateBetween(LocalDate from, LocalDate to) {
		String SQL = "SELECT e.* FROM airdate a INNER JOIN event e ON e.ID =  a.airdateID WHERE Date  BETWEEN ? and ?";
		Set<Event> events = new HashSet<>(jdbcTemplateObject.query(SQL, new Object[] { from, to }, new EventRowMapper(this)));
		return events;
	}

	public NavigableMap<LocalDateTime, Auditorium> getAuditoriumsByEventId(Long id) {
		String SQL = "SELECT audit.*,ar.Date FROM airdate ar INNER JOIN auditorium audit ON ar.AuditoriumId = audit.ID WHERE ar.EventId  = ?";
		NavigableMap<LocalDateTime, Auditorium> airdateAuditoriums = new TreeMap<>();
		List<Entry<LocalDateTime, Auditorium>> query = jdbcTemplateObject.query(SQL, new Object[] { id }, airdateAuditoriumRowMapper);
		query.stream().map(en -> airdateAuditoriums.put(en.getKey(), en.getValue()));
		return airdateAuditoriums;
	}

	public void save(Long eventId, NavigableMap<LocalDateTime, Auditorium> airDates) {
		String SQL = "insert into airdate (EventId,Date,AuditoriumId) values (?, ?, ?)";
		List<Entry<LocalDateTime, Auditorium>> airDatesList = new ArrayList<>(airDates.entrySet());

		jdbcTemplateObject.batchUpdate(SQL, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, eventId);
				ps.setDate(2, java.sql.Date.valueOf(airDatesList.get(i).getKey().toLocalDate()));
				ps.setLong(3, airDatesList.get(i).getValue().getId());
			}

			@Override
			public int getBatchSize() {
				return airDatesList.size();
			}

		});
	}

	public void remove(Long id) {
		String SQL = "delete from airdate where EventId = ?";
		jdbcTemplateObject.update(SQL, id);
	}

}
