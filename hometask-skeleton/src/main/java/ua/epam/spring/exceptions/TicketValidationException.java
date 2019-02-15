package ua.epam.spring.exceptions;

public class TicketValidationException  extends Exception{

	private static final long serialVersionUID = 5324986802664803354L;
	
	  public TicketValidationException() {}
	
	  public TicketValidationException(String message) {
	        super(message);
	    }
}
