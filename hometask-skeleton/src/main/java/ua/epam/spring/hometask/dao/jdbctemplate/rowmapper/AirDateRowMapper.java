package ua.epam.spring.hometask.dao.jdbctemplate.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AirDateRowMapper implements RowMapper<LocalDateTime> {

	@Override
	public LocalDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new java.sql.Timestamp(rs.getDate("Date").getTime()).toLocalDateTime();
	}

}
