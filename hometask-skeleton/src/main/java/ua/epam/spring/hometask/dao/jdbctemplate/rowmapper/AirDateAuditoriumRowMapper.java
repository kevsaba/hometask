package ua.epam.spring.hometask.dao.jdbctemplate.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ua.epam.spring.hometask.domain.Auditorium;

@Component
public class AirDateAuditoriumRowMapper implements RowMapper<Map.Entry<LocalDateTime, Auditorium>> {

	@Override
	public Map.Entry<LocalDateTime, Auditorium> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Auditorium auditorium = new Auditorium();

		auditorium.setId(rs.getLong("ID"));
		auditorium.setName(rs.getString("Name"));
		auditorium.setNumberOfSeats(rs.getLong("NumberOfSeats"));
		Set<Long> vipsLong = new HashSet<>();
		Set.of(rs.getString("VipSeats").split(",")).stream().forEach(s -> vipsLong.add(Long.valueOf(s)));
		auditorium.setVipSeats(vipsLong);

		LocalDateTime date = new java.sql.Timestamp(rs.getDate("Date").getTime()).toLocalDateTime();

		Map.Entry<LocalDateTime, Auditorium> entrySet = new AbstractMap.SimpleEntry<LocalDateTime, Auditorium>(date, auditorium);
		return entrySet;
	}

}
