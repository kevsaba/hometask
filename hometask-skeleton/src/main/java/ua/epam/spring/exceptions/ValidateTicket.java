package ua.epam.spring.exceptions;

import java.util.Set;

import ua.epam.spring.hometask.domain.Ticket;

public class ValidateTicket {

	static boolean ret= true;
	
	public static boolean ticketValidation(Set<Ticket> tickets)  {
		
		tickets.forEach(t->{
			if (t.getEvent() == null || t.getDateTime() == null || t.getSeat() == 0) {
					ret= false;
					try {
						throw new TicketValidationException("Invalid ticket creation 1 or more item is not valid");
					} catch (TicketValidationException e) {
						e.printStackTrace();
					}
			}
			});
		return ret;
		
	}
}
