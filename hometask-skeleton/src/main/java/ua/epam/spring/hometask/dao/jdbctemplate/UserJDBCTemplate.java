package ua.epam.spring.hometask.dao.jdbctemplate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.epam.spring.hometask.domain.User;

public class UserJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public UserJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void save(User user) {
		String SQL = "insert into user (FirstName,LastName,Email,Birthday) values (?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday());
		System.out.println("User created");
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
