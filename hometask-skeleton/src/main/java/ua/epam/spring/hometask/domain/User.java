package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Table(name = "user")
public class User extends DomainObject {

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Birthday")
	private LocalDateTime birthday;

	private NavigableSet<Ticket> tickets = new TreeSet<>();

	private Set<Event> luckyEvents = new HashSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public NavigableSet<Ticket> getTickets() {
		return tickets;
	}

	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}

	public void addTickets(Set<Ticket> tickets) {
		this.tickets.addAll(tickets);
	}

	public void setTickets(NavigableSet<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void addLuckyEvents(Event event) {
		luckyEvents.add(event);
	}

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

}
