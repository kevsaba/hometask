package ua.epam.spring.hometask.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.domain.User;

@Repository
public class UserDAO {

	private static Map<Long,User> users= new HashMap<>();
	private Long idGenerated = 0L;
	
	public User save(User user) {
		user.setId(++idGenerated);
		users.put(user.getId(), user);
		return user;
	}

	public void remove(User user) {
		users.remove(user.getId());
	}

	public User getById(Long id) {
		return users.get(id);
	}

	public Collection<User> getAll() {
		return users.values();
	}

	public User getByEmail(String email) {
		 return users.values().stream().filter(u->u.getEmail().equals(email)).findFirst().get();
	}

}
