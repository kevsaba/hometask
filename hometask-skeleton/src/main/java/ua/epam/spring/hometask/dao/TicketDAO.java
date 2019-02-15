package ua.epam.spring.hometask.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import ua.epam.spring.hometask.domain.Ticket;

@Repository
public class TicketDAO {

	private static Set<Ticket> tickets= new HashSet<>();
	private Long idGenerated = 0L;
	
	
	public void save(Set<Ticket> ticketsToBook) {
		ticketsToBook.stream().forEach(t->t.setId(++idGenerated));
		tickets.addAll(ticketsToBook);
	}

	public Set<Ticket> getAllTickets(){
		return tickets;
	}


	
}
