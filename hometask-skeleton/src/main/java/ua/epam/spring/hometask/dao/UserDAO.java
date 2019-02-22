package ua.epam.spring.hometask.dao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.jdbctemplate.UserJDBCTemplate;
import ua.epam.spring.hometask.domain.User;

@Repository
public class UserDAO {

	@Autowired
	UserJDBCTemplate userJDBCTemplate;

	public User save(User user) {
		return userJDBCTemplate.save(user);
	}

	public void remove(User user) {
		userJDBCTemplate.remove(user);
	}

	public User getById(Long id) {
		return userJDBCTemplate.getById(id);
	}

	public Collection<User> getAll() {
		return userJDBCTemplate.getAll();
	}

	public User getByEmail(String email) {
		return userJDBCTemplate.getByEmail(email);
	}

}
