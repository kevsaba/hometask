package ua.epam.spring.hometask.dao.jdbctemplate.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import ua.epam.spring.hometask.domain.Auditorium;

public class AuditoriumRowMapper implements RowMapper<Auditorium> {

	@Override
	public Auditorium mapRow(ResultSet rs, int rowNum) throws SQLException {
		Auditorium auditorium = new Auditorium();

		auditorium.setId(rs.getLong("ID"));
		auditorium.setName(rs.getString("Name"));
		auditorium.setNumberOfSeats(rs.getLong("NumberOfSeats"));
		Set<Long> vipsLong = new HashSet<>();
		Set.of(rs.getString("VipSeats").split(",")).stream().forEach(s -> vipsLong.add(Long.valueOf(s)));
		auditorium.setVipSeats(vipsLong);

		return auditorium;
	}

}
