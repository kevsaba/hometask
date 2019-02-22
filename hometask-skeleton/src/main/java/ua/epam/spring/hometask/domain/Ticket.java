package ua.epam.spring.hometask.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Yuriy_Tkach
 */
@Entity
@Table(name = "ticket")
public class Ticket extends DomainObject implements Comparable<Ticket> {

	@Column(name = "UserId")
	private User user;

	@Column(name = "EventId")
	private Event event;

	@Column(name = "DateTime")
	private LocalDateTime dateTime;

	@Column(name = "Seat")
	private long seat;

	@Column(name = "Price")
	private double price = 0.0;

	@Column(name = "IsLuckyTicket")
	private boolean isLuckyTicket = false;

	public Ticket(User user, Event event, LocalDateTime dateTime, long seat) {
		this.user = user;
		this.event = event;
		this.dateTime = dateTime;
		this.seat = seat;
	}

	public User getUser() {
		return user;
	}

	public Event getEvent() {
		return event;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public long getSeat() {
		return seat;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void isLuckyTicket() {
		this.isLuckyTicket = true;
	}

	public boolean getIsLuckyTicket() {
		return this.isLuckyTicket;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateTime, event, seat);
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
		Ticket other = (Ticket) obj;
		if (dateTime == null) {
			if (other.dateTime != null) {
				return false;
			}
		} else if (!dateTime.equals(other.dateTime)) {
			return false;
		}
		if (event == null) {
			if (other.event != null) {
				return false;
			}
		} else if (!event.equals(other.event)) {
			return false;
		}
		if (seat != other.seat) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Ticket other) {
		if (other == null) {
			return 1;
		}
		int result = dateTime.compareTo(other.getDateTime());

		if (result == 0) {
			result = event.getName().compareTo(other.getEvent().getName());
		}
		if (result == 0) {
			result = Long.compare(seat, other.getSeat());
		}
		return result;
	}

}
