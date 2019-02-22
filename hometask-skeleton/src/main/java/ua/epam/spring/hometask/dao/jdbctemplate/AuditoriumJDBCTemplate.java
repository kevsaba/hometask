package ua.epam.spring.hometask.dao.jdbctemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.AuditoriumRowMapper;
import ua.epam.spring.hometask.domain.Auditorium;

public class AuditoriumJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public AuditoriumJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void save(Auditorium auditorium) {
		String SQL = "insert into auditorium (Name,NumberOfSeats,VipSeats) values (?, ?, ?)";
		jdbcTemplateObject.update(SQL, auditorium.getName(), auditorium.getNumberOfSeats(), auditorium.getVipSeats().stream().map(n -> n.toString()).collect(Collectors.joining(",")));
	}

	public Auditorium getById(Long id) {
		String SQL = "select * from auditorium where id = ?";
		Auditorium auditorium = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new AuditoriumRowMapper());
		return auditorium;
	}

	public Set<Auditorium> getAll() {
		String SQL = "select * from auditorium";
		List<Auditorium> auditoriums = jdbcTemplateObject.query(SQL, new AuditoriumRowMapper());
		return Set.copyOf(auditoriums);
	}

	public Auditorium getByName(String name) {
		String SQL = "select * from auditorium where Name = ?";
		Auditorium auditorium = jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new AuditoriumRowMapper());
		return auditorium;
	}

}
