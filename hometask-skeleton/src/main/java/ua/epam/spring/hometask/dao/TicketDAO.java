package ua.epam.spring.hometask.dao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.dao.jdbctemplate.TicketJDBCTemplate;
import ua.epam.spring.hometask.domain.Ticket;

@Repository
public class TicketDAO {

	@Autowired
	TicketJDBCTemplate ticketJDBCTemplate;

	public void save(Set<Ticket> ticketsToBook) {
		ticketsToBook.stream().forEach(t -> ticketJDBCTemplate.save(t));
	}

	public Set<Ticket> getAllTickets() {
		return ticketJDBCTemplate.getAll();
	}

}
