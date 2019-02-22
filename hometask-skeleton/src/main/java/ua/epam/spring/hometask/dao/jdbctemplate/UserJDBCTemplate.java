package ua.epam.spring.hometask.dao.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.UserRowMapper;
import ua.epam.spring.hometask.domain.User;

public class UserJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public UserJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public User save(User user) {
		String SQL = "insert into user (FirstName,LastName,Email,Birthday) values (?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "FirstName", "LastName", "Email", "Birthday" });
				ps.setString(1, user.getFirstName());
				ps.setString(2, user.getLastName());
				ps.setString(3, user.getEmail());
				ps.setDate(4, java.sql.Date.valueOf(user.getBirthday().toLocalDate()));
				return ps;
			}
		}, keyHolder);

		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	public void remove(User user) {
		String SQL = "delete from User where id = ?";
		jdbcTemplateObject.update(SQL, user.getId());
		System.out.println("Deleted Record with ID = " + user.getId());
	}

	public User getById(Long id) {
		String SQL = "select * from User where id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserRowMapper());
		return user;
	}

	public List<User> getAll() {
		String SQL = "select * from User";
		List<User> users = jdbcTemplateObject.query(SQL, new UserRowMapper());
		return users;
	}

	public User getByEmail(String email) {
		String SQL = "select * from User where email = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { email }, new UserRowMapper());
		return user;
	}

}
