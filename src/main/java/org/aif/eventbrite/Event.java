package org.aif.eventbrite;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	private Pagination pagination;
	private List<Attendees> attendees;

	public List<Attendees> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendees> attendees) {
		this.attendees = attendees;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	} 
}
