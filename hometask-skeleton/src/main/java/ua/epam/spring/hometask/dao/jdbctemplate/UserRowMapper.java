package ua.epam.spring.hometask.dao.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ua.epam.spring.hometask.domain.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = new User();

		user.setId(rs.getLong("ID"));
		user.setFirstName(rs.getString("FirstName"));
		user.setLastName(rs.getString("LastName"));
		user.setEmail(rs.getString("Email"));
		user.setBirthday(new java.sql.Timestamp(rs.getDate("Birthday").getTime()).toLocalDateTime());

		return user;
	}

}
