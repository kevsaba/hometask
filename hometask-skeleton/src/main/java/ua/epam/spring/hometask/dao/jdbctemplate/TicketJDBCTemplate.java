package ua.epam.spring.hometask.dao.jdbctemplate;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import ua.epam.spring.hometask.dao.jdbctemplate.rowmapper.TicketRowMapper;
import ua.epam.spring.hometask.domain.Ticket;

public class TicketJDBCTemplate {

	@Autowired
	private TicketRowMapper ticketRowMapper;

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public TicketJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void save(Ticket ticket) {
		String SQL = "insert into ticket (DateTime,Seat,Price,EventId,UserId,IsLuckyTicket) values (?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, ticket.getDateTime(), ticket.getSeat(), ticket.getPrice(), ticket.getEvent().getId(), ticket.getUser().getId(), ticket.getIsLuckyTicket());
		System.out.println("Ticket created");
	}

	public void remove(Ticket ticket) {
		String SQL = "delete from ticket where id = ?";
		jdbcTemplateObject.update(SQL, ticket.getId());
		System.out.println("Deleted Record with ID = " + ticket.getId());
	}

	public Ticket getById(Long id) {
		String SQL = "select * from ticket where id = ?";
		Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, ticketRowMapper);
		return ticket;
	}

	public Set<Ticket> getAll() {
		String SQL = "select * from ticket";
		Set<Ticket> tickets = new HashSet<>(jdbcTemplateObject.query(SQL, ticketRowMapper));
		return tickets;
	}

	public Ticket getByEmail(String email) {
		String SQL = "select * from ticket where email = ?";
		Ticket ticket = jdbcTemplateObject.queryForObject(SQL, new Object[] { email }, ticketRowMapper);
		return ticket;
	}

}
